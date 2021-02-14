/**
 * Basic navigation implementation.
 */

// Interface representing a navigation control and the page it displays.
interface NavPair {
  control: HTMLElement;
  page: HTMLElement;
}

// Each item in `navPair` represents a pair of control + the page it displays
// when clicked. Whenver a control is clicked, it will be selected and its page
// will be shown; all other controls will be deselected and other pages will be
// hidden.
export function configureNavigation(navPairs: NavPair[]) {
  for (let index = 0; index < navPairs.length; ++index) {
    const {control, page} = navPairs[index];

    control.addEventListener('mousedown', (e: Event) => {
      // Highlight the nav / select the page.
      if (!control.classList.contains('nav-selected')) {
        control.classList.add('nav-selected');
      }
      page.classList.remove('hidden');

      // Deselect all other items and update the view.
      for (let otherIdx = 0; otherIdx < navPairs.length; ++otherIdx) {
        if (otherIdx != index) {
          navPairs[otherIdx].control.classList.remove('nav-selected');
          navPairs[otherIdx].page.classList.add('hidden');
        }
      }
    });
  }
}
