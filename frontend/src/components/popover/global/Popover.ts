import PopoverItem from "./PopoverItem.svelte";
import type {ComponentInfo, SveltePopoverOptions} from "./PopoverTypes";
import {validateOptions} from "./utils";


class Popover {

    private components: ComponentInfo[] = [];

    show(options: SveltePopoverOptions): string {
        const id = validateOptions(options);

        options.target.setAttribute('aria-expanded', 'true');

        const close = () => {
            this.destroy(componentInfo);
        }

        if (options.component.props) {
            options.component.props['closePopover'] = close;
        } else {
            options.component.props = {closePopover: close}
        }


        const component = new PopoverItem({
            target: document.getElementById("popover-holder"),
            props: {
                options: options
            }
        })

        document.getElementById(`popover-${options.id}`).focus();

        component.$on("close", close);

        let componentInfo: ComponentInfo = {
            id: id,
            target: options.target,
            component: component
        };

        this.components.push(componentInfo);


        return id;
    }


    destroy(item: ComponentInfo) {
        const index = this.components.indexOf(item);
        this.components.splice(index, 1);

        item.target.removeAttribute('aria-expanded');
        item.target.dispatchEvent(new CustomEvent('popupClose'));
        item.component.$destroy();
    }

    destroyOther(item: ComponentInfo) {
        this.components.filter(it => it !== item).forEach(it => this.destroy(it));
    }

    destroyAll() {
        this.components.forEach(it => this.destroy(it))
    }
}

const popover = new Popover();

const popoverTrigger = (node: HTMLElement, options: SveltePopoverOptions) => {

    const createPopover = (event) => {
        if (options.stopPropagation) event.stopPropagation();
        options.target = node;
        popover.show(options);
    };


    node.addEventListener('click', createPopover)

    return {
        destroy() {
            node.removeEventListener('click', createPopover);
        }
    }
}


export {popover, popoverTrigger}