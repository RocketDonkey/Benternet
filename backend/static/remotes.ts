import {installCommands} from './commands';
import {configureNavigation} from './navigation';

// Class that wires up the UI-to-server interactions.
class RemoteConfig {
  constructor() {
    // Initalize all of the device commands.
    installCommands();

    this.setupNavigation();
  }

  // Configures the navigation menu/behavior.
  private setupNavigation() {
    const homeBtn = document.getElementById('home-btn');
    const homePage = document.getElementById('home-page');

    const fireplaceBtn = document.getElementById('fireplace-btn');
    const fireplacePage = document.getElementById('fireplace-page');

    const lightsBtn = document.getElementById('lights-btn');
    const lightsPage = document.getElementById('lights-page');

    const tvBtn = document.getElementById('tv-btn');
    const tvPage = document.getElementById('tv-page');

    configureNavigation([
      {control : homeBtn, page : homePage},
      {control : fireplaceBtn, page : fireplacePage},
      {control : lightsBtn, page : lightsPage},
      {control : tvBtn, page : tvPage},
    ]);
  }
}

// Fire it up.
new RemoteConfig();
