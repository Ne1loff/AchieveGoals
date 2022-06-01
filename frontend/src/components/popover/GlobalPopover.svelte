<style>
    .own-popover {

    }
</style>

<div class="own-popover">
    <Content on:open on:setOpen={setOpen} {placement} {targetRef} {zIndex} {arrow} {action} {overlayColor}
             {arrowColor} {preventDefault} {stopPropagation}>
        <svelte:component this={contentComponent} {...componentProps}/>
    </Content>
</div>

<script>
    const DEFAULT_Z_INDEX = 1000;
    import Content from './Content.svelte';
    import {createEventDispatcher} from 'svelte';

    export let targetRef;
    export let contentComponent;
    export let componentProps;

    const dispatch = createEventDispatcher();

    export let action = 'click';
    export let zIndex = DEFAULT_Z_INDEX;
    export let arrow = true;
    export let placement = 'auto';
    export let arrowColor = 'var(--cds-field)';
    export let overlayColor = 'rgba(0, 0, 0, 0.02)';
    export let preventDefault = false;
    export let stopPropagation = false;

    export let open = true;

    const setOpen = () => {
        open = !open;
        if (!open) {
            dispatch('close');
        }
    };
    const eventClick = e => {
        if (preventDefault) e.preventDefault();
        if (stopPropagation) e.stopPropagation();
        setOpen();
    };
    const eventMouseOut = ({relatedTarget}) => {
        if (relatedTarget.id === 'overlay' && !open) {
            setOpen();
        }
    };

    const onTouchEnd = action === 'click' ? eventClick : null;
    const onClick = action === 'click' ? eventClick : null;

    const setOpenTrue = () => (open = true);

    const onMouseOver = action === 'hover' ? setOpenTrue : null;
    const onMouseOut = action === 'hover' ? eventMouseOut : null;
</script>