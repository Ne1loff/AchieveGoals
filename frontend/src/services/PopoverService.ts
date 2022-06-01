import GlobalPopover from "../components/popover/GlobalPopover.svelte";

class PopoverService {

    private target = document.getElementById("popoverContainer");

    create = (targetRef, contentComponent, componentProps) => {
        const popover = new GlobalPopover({
            target: this.target,
            props: {
                targetRef: targetRef,
                contentComponent: contentComponent,
                componentProps: componentProps,
                open: true
            }
        });
        popover.$on('close', () => {
            console.log('destroy');
            popover.$destroy();
        });
    }

}

export default PopoverService;