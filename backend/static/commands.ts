// Connects the commands in `DEVICE_CONFIGS` to their respective UI elements.
export function installCommands() {
  const commands = new Commands();
  commands.initialize();
}

interface ActionParams {
  // The DOM element ID that when clicked will trigger `action`.
  elementId: string;
  // The action to perform when clicking `elementId`. This must map to an action
  // understood by the server.
  action: string;
}

interface DeviceConfig {
  // The name of the device as understood by the server.
  device: string;
  // The actions that will be configured for this device.
  actions: ActionParams[];
}

// The structure of a command sent to the server.
interface Command {
  device: string;
  action: string;
}

// Per-device actions. To add a new action:
//
//   1. Add a new DOM element
//   2. (If needed) Add a new device config
//   3. Add the new action to the device's `actions`
const DEVICE_CONFIGS: DeviceConfig[] = [
  {
    device : 'fireplace',
    actions : [
      {elementId : 'fireplace-power', action : 'togglePower'},
      {elementId : 'fireplace-heat', action : 'toggleHeat'},
      {elementId : 'fireplace-lights', action : 'toggleLights'},
    ],
  },
  {
    device : 'tv',
    actions : [
      {elementId : 'tv-power', action : 'togglePower'},
      {elementId : 'tv-volume-up', action : 'volumeUp'},
      {elementId : 'tv-volume-down', action : 'volumeDown'},
      {elementId : 'tv-mute', action : 'toggleMute'},
    ],
  },
  {
    device : 'downstairs_lights',
    actions : [
      {elementId : 'downstairs-on', action : 'on'},
      {elementId : 'downstairs-off', action : 'off'},
    ],
  },
  {
    device : 'upstairs_lights',
    actions : [
      {elementId : 'upstairs-on', action : 'on'},
      {elementId : 'upstairs-off', action : 'off'},
    ],
  },
];

// The API endpoint for executing commands.
const API_SERVER = 'http://remotes/command';

class Commands {
  initialize() {
    for (let deviceConfig of DEVICE_CONFIGS) {
      const {device, actions} = deviceConfig;
      for (let {elementId, action} of actions) {
        this.attachCommand(elementId, {device, action});
      }
    }

    this.setupMacros();
  }

  // Sets up commands that invoke multiple commands.
  private setupMacros() {
    // Turn on all the lights.
    this.attachCommands('all-on', [
      {device : 'downstairs_lights', action : 'on'},
      {device : 'upstairs_lights', action : 'on'},
    ]);

    // Turn off all the lights.
    this.attachCommands('all-off', [
      {device : 'downstairs_lights', action : 'off'},
      {device : 'upstairs_lights', action : 'off'},
    ]);

    // Turn on the downstairs lights / fireplace / TV.
    this.attachCommands('set-mood', [
      {device : 'downstairs_lights', action : 'on'},
      {device : 'tv', action : 'togglePower'},
      {device : 'fireplace', action : 'togglePower'},
      {device : 'upstairs_lights', action : 'off'},
    ]);

    // Shut down everything downstairs.
    this.attachCommands('shut-down', [
      {device : 'tv', action : 'togglePower'},
      {device : 'fireplace', action : 'togglePower'},
      {device : 'downstairs_lights', action : 'off'},
    ]);
  }

  // Convenience method for attaching a single command.
  private attachCommand(elementId: string, command: Command) {
    this.attachCommands(elementId, [ command ]);
  }

  // Attaches the provided `commands` to the DOM element represented by
  // `elementId`.
  private attachCommands(elementId: string, commands: Command[]) {
    document.getElementById(elementId).addEventListener(
        'mousedown', (e: Event) => this.sendCommands(commands));
  }

  // Sends `commands` to the server for execution.
  private sendCommands(commands: Command[]) {
    fetch(API_SERVER, {
      method : 'POST',
      headers : {'Content-Type' : 'application/json'},
      body : JSON.stringify({commands}),
    })
        .then(data => data.json())
        .then(res => console.log(res));
  }
}
