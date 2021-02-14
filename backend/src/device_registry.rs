use std::collections::HashMap;
use std::process::Command;

pub type ActionMap = HashMap<String, String>;
pub type DeviceMap = HashMap<String, Device>;

// The DeviceRegistry contains all devices that can perform actions.
//
// A Device contains a `name` and an `action`. A Device can be looked up in the registry by its
// `name`, and looking up `action` will return a command that can be used to perform that action.

// A Device that can be added to the DeviceRegistry.
//
// A Device represents an object that can perform an action. For example, a TV remote is a device
// that can perform actions like 'turn the TV on'.
pub struct Device {
    pub name: String,
    pub actions: ActionMap,
}

impl Device {
    // Constructs a new Device.
    pub fn new(name: &str, actions: ActionMap) -> Device {
        Device {
            name: name.to_string(),
            actions: actions,
        }
    }

    // Returns a Command than can be executed to perform `action` on this device.
    //
    // For example, the `action` 'irsend send_once tv KEY_POWER` would be converted into a command
    // that when executed would invoke `irsend` and send a 'KEY_POWER' command to the 'tv' remote.
    pub fn get_command(&self, action: String) -> std::process::Command {
        let command_string = self.actions.get(&action).unwrap();

        println!("Executing command: {}", command_string);

        // Split the command into pieces.
        let args = command_string
            .split_whitespace()
            .collect::<Vec<&str>>()
            .clone();

        let mut args_iter = args.iter();

        // The first item is the executable; everything else is an arg.
        let binary = args_iter.next().unwrap();
        let mut command = Command::new(binary);
        command.args(args_iter);

        command
    }
}

// Helper for building an ActionMap from a vector of strings.
fn build_action_map(actions: Vec<(&str, &str)>) -> ActionMap {
    let mut action_map = ActionMap::new();
    for action in actions {
        action_map.insert(String::from(action.0), String::from(action.1));
    }
    action_map
}

// A registry that contains Devices, where each Device can perform a set of actions.
pub struct DeviceRegistry {
    // The devices managed by this registry.
    devices: DeviceMap,
}

impl DeviceRegistry {
    // Returns a copy of the registered devices. Returning a reference seems preferable, but I am a
    // failure and couldn't find a reasonable way to do that and pass this to rocket::ignite.
    pub fn devices(self) -> DeviceMap {
        self.devices
    }

    // Helper that registers `device` in the registry.
    fn register_device(&mut self, device: Device) {
        self.devices.insert(device.name.clone(), device);
    }

    // Initializes a DeviceRegistry with the available devices.
    pub fn init() -> DeviceRegistry {
        let mut device_registry = DeviceRegistry::new();

        // Fireplace.
        device_registry.register_device(Device::new(
            /*name=*/ "fireplace",
            /*actions=*/
            build_action_map(vec![
                ("togglePower", "irsend send_once fireplace KEY_POWER"),
                ("toggleHeat", "irsend send_once fireplace KEY_POWER2"),
                (
                    "toggleLights",
                    "irsend send_once fireplace KEY_LIGHTS_TOGGLE",
                ),
            ]),
        ));

        // TV.
        device_registry.register_device(Device::new(
            /*name=*/ "tv",
            /*actions=*/
            build_action_map(vec![
                ("togglePower", "irsend send_once tv_remote_real KEY_POWER"),
                ("volumeUp", "irsend send_once tv_remote_real KEY_VOLUMEUP"),
                (
                    "volumeDown",
                    "irsend send_once tv_remote_real KEY_VOLUMEDOWN",
                ),
                ("toggleMute", "irsend send_once tv_remote_real KEY_MUTE"),
            ]),
        ));

        // Lights (downstairs).
        device_registry.register_device(Device::new(
            /*name=*/ "downstairs_lights",
            /*actions=*/
            build_action_map(vec![
                ("on", "codesend 70963 1 196"),
                ("off", "codesend 70972 1 196"),
            ]),
        ));

        // Lights (Upstairs).
        device_registry.register_device(Device::new(
            /*name=*/ "upstairs_lights",
            /*actions=*/
            build_action_map(vec![
                ("on", "codesend 71107 1 196 "),
                ("off", "codesend 71116 1 196 "),
            ]),
        ));

        device_registry
    }

    // Private contructor.
    fn new() -> DeviceRegistry {
        DeviceRegistry {
            devices: DeviceMap::new(),
        }
    }
}
