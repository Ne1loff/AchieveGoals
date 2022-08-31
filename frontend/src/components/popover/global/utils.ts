import type {SveltePopoverOptions} from "./PopoverTypes";

const validateOptions = (options: SveltePopoverOptions): string => {
    if (!options.placement) options.placement = 'auto';
    if (options.useOverlay === undefined) options.useOverlay = true;

    let id = Math.random().toString(4).slice(2);

    if (!options.id) options.id = id;
    else id = options.id;

    return id;
}

export {validateOptions}