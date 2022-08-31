import type {SvelteComponent} from "svelte";

type SveltePopOverPlacement = 'auto' |
    'top-start' | 'top-center' | 'top-end' |
    'left-start' | 'left-center' | 'left-end' |
    'right-start' | 'right-center' | 'right-end' |
    'bottom-start' | 'bottom-center' | 'bottom-end';

interface SvelteComponentOptions {
    src: typeof SvelteComponent,
    props?: { [key: string]: any }
}

interface SveltePopoverOptions {
    id?: string,
    target?: HTMLElement,
    fromComponent?: boolean,
    component?: SvelteComponentOptions,
    stopPropagation?: boolean,
    useOverlay?: boolean,
    placement?: SveltePopOverPlacement,
    style?: { [key: string]: string },
    classStyle: string | string[]
}

interface ComponentInfo {
    id: string,
    target: HTMLElement,
    component: SvelteComponent
}

export type {SveltePopOverPlacement, SvelteComponentOptions, SveltePopoverOptions, ComponentInfo}