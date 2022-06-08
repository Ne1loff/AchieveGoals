import type {SvelteComponent} from "svelte";
import PopoverItem from "./PopoverItem.svelte";

class Popover {

    private components: {
        id: string,
        target: HTMLElement,
        component: SvelteComponent
    }[] = [];

    show(options: SveltePopoverOptions): string {
        if (!options.placement) options.placement = 'auto';

        options.target.setAttribute('aria-expanded', 'true');

        const component = new PopoverItem({
            target: document.getElementsByClassName("_popoverContainer")[0],
            props: {
                options: options
            }
        })
        const id = Math.random().toString(4).slice(2)
        this.components.push({
            id: id,
            target: options.target,
            component: component
        });

        component.$on("close", () => {
            this.destroy(id);
        })

        return id;
    }

    destroy(id: string) {
        const item = this.components.find((it) => it.id === id);
        const index = this.components.indexOf(item);
        this.components.slice(index, 1);

        item.target.removeAttribute('aria-expanded');
        item.component.$destroy();
    }
}


interface SveltePopoverOptions {
    target?: HTMLElement,
    component: {
        src: typeof SvelteComponent,
        props?: { [key: string]: any }
    }
    placement?: 'auto' |
        'top-start' | 'top-center' | 'top-end' |
        'left-start' | 'left-center' | 'left-end' |
        'right-start' | 'right-center' | 'right-end' |
        'bottom-start' | 'bottom-center' | 'bottom-end',
    style?: { [key: string]: string }
}


let popover = new Popover();

const popoverTrigger = (node: HTMLElement, options: SveltePopoverOptions) => {

    const createPopover = () => {
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

export type {SveltePopoverOptions};
export {popover, popoverTrigger}