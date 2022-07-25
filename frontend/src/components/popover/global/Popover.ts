import type {SvelteComponent} from "svelte";
import PopoverItem from "./PopoverItem.svelte";


interface SvelteComponentOptions {
    src: typeof SvelteComponent,
    props?: { [key: string]: any }
}

interface SveltePopoverOptions {
    id?: string,
    target?: HTMLElement,
    component: SvelteComponentOptions,
    useOverlay?: boolean,
    placement?: 'auto' |
        'top-start' | 'top-center' | 'top-end' |
        'left-start' | 'left-center' | 'left-end' |
        'right-start' | 'right-center' | 'right-end' |
        'bottom-start' | 'bottom-center' | 'bottom-end',
    style?: { [key: string]: string },
    classStyle: string | string[]
}

interface ComponentInfo {
    id: string,
    target: HTMLElement,
    component: SvelteComponent
}

class Popover {

    private components: ComponentInfo[] = [];

    show(options: SveltePopoverOptions): string {
        if (!options.placement) options.placement = 'auto';
        if (options.useOverlay === undefined) options.useOverlay = true;

        let id = Math.random().toString(4).slice(2);

        if (!options.id) options.id = id;
        else id = options.id;

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

    destroyAll() {
        this.components.forEach(it => this.destroy(it))
    }
}

const popover = new Popover();

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

export type {SvelteComponentOptions, SveltePopoverOptions};
export {popover, popoverTrigger}