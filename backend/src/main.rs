#![feature(decl_macro)]
#![feature(proc_macro_hygiene)]

mod device_registry;

use rocket::State;
use rocket::{post, routes};
use rocket_contrib::json;
use rocket_contrib::json::{Json, JsonValue};
use rocket_contrib::serve::StaticFiles;
use rocket_cors::{AllowedHeaders, AllowedOrigins, Error};
use serde_derive::{Deserialize, Serialize};

use std::str::FromStr;
use std::{thread, time};

// A command to perform `action` given `device`.
//
// `device` should map to a Device object in `device_registry`. `action` should map to one of the
// actions on the Device.
#[derive(Serialize, Deserialize)]
struct DeviceCommand {
    // The name of the device targeted by this command. See `device_registry` for options.
    device: String,
    // The action to execute on the provided device.
    action: String,
}

#[derive(Serialize, Deserialize)]
struct CommandRpc {
    commands: Vec<DeviceCommand>,
}

// Handles a command to perform an action using a device.
#[post("/command", format = "json", data = "<command_data>")]
fn handle_command(
    device_map: State<device_registry::DeviceMap>,
    command_data: Json<CommandRpc>,
) -> JsonValue {
    // Unpack the RPC.
    let command_rpc = command_data.into_inner();

    for command in command_rpc.commands {
        // Extract the command from the request.
        let device = device_map.get(&command.device).unwrap();

        // Retrieve the command to execute for this device/action.
        let mut exec_command = device.get_command(command.action);
        let output = exec_command.output().expect("Failed to execute command");
        println!("status: {}", output.status);

        // Sleep before executing the next command to avoid any interference
        // issues.
        thread::sleep(time::Duration::from_millis(500));
    }

    // TODO: Handle errors/missing/devices/etc.
    json!({
      "status": "ok"
    })
}

// Setup CORS for all handlers.
fn cors_options() -> rocket_cors::CorsOptions {
    let allowed_origins = AllowedOrigins::all();
    let allowed_methods: rocket_cors::AllowedMethods = ["Get", "Post", "Delete"]
        .iter()
        .map(|s| FromStr::from_str(s).unwrap())
        .collect();

    rocket_cors::CorsOptions {
        allowed_origins,
        allowed_methods: allowed_methods,
        allowed_headers: AllowedHeaders::all(),
        allow_credentials: true,
        ..Default::default()
    }
}

fn main() -> Result<(), Error> {
    let device_registry = device_registry::DeviceRegistry::init();

    rocket::ignite()
        .mount("/", StaticFiles::from("static"))
        .mount("/", routes![handle_command])
        .attach(cors_options().to_cors().expect("To not fail"))
        .manage(device_registry.devices())
        .launch();

    Ok(())
}
