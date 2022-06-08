<script lang="ts">
    import type {SveltePopoverOptions} from "./Popover";
    import {createEventDispatcher, onMount} from "svelte";

    const dispatch = createEventDispatcher();

    export let options: SveltePopoverOptions;

    let style: string = '';
    let positionStyle: string = '';

    let contentRef: HTMLElement;

    const close = () => {
        dispatch('close')
    }

    const setStyle = () => {
        if (options.style) {
            const names: string[] = Object.getOwnPropertyNames(options.style);
            style = names.map(name => `${name}:${options.style[name]}`).join('');
        }
    }

    const calculatePosition = () => {
        const targetBound = options.target.getBoundingClientRect();
        const contentBound = contentRef.getBoundingClientRect();

        const {innerWidth, innerHeight} = window;


        // position
        const calcLeft = targetBound.x - contentBound.width;
        const calcRight = targetBound.x + targetBound.width;
        const calcTop = targetBound.y - contentBound.height;
        const calcBottom = targetBound.y + targetBound.height;

        // y offset
        const calcHorizonTop = targetBound.y + targetBound.height - contentBound.height;
        const calcHorizonCenter = targetBound.y + targetBound.height / 2 - contentBound.height / 2;
        const calcHorizonBottom = targetBound.y;

        // x offset
        const calcVerticalLeft = targetBound.x + targetBound.width - contentBound.width;
        const calcVerticalCenter = targetBound.x + targetBound.width / 2 - contentBound.width / 2;
        const calcVerticalRight = targetBound.x;


        // cover
        const coverLeft = calcLeft;
        const coverRight = innerWidth - (calcRight + contentBound.width);
        const coverTop = calcTop;
        const coverBottom = innerHeight - (calcBottom + contentBound.height);

        const coverHorizonTop = calcHorizonTop;
        const coverHorizonBottom = innerHeight - (calcHorizonBottom + contentBound.height);
        const coverVerticalLeft = calcVerticalLeft;
        const coverVerticalRight = innerWidth - (calcVerticalRight + contentBound.width);


        const styles = {
            topStart: `top:${calcTop}px;left:${calcVerticalLeft}px`,
            topCenter: `top:${calcTop}px;left:${calcVerticalCenter}px`,
            topEnd: `top:${calcTop}px;left:${calcVerticalRight}px`,

            leftStart: `top:${calcHorizonTop}px;left:${calcLeft}px`,
            leftCenter: `top:${calcHorizonCenter}px;left:${calcLeft}px`,
            leftEnd: `top:${calcHorizonBottom}px;left:${calcLeft}px`,

            rightStart: `top:${calcHorizonTop}px;left:${calcRight}px`,
            rightCenter: `top:${calcHorizonCenter}px;left:${calcRight}px`,
            rightEnd: `top:${calcHorizonBottom}px;left:${calcRight}px`,

            bottomStart: `top:${calcBottom}px;left:${calcVerticalLeft}px`,
            bottomCenter: `top:${calcBottom}px;left:${calcVerticalCenter}px`,
            bottomEnd: `top:${calcBottom}px;left:${calcVerticalRight}px;`,
        };

        const pos = [
            {
                at: 'top-start',
                cover: {
                    left: coverVerticalLeft,
                    top: coverTop,
                    right: innerWidth - (coverVerticalLeft + contentBound.width),
                    bottom: innerHeight - (coverTop + contentBound.height)
                },
                style: styles.topStart,
            },
            {
                at: 'top-center',
                cover: {
                    left: calcVerticalCenter,
                    top: coverTop,
                    right: innerWidth - (calcVerticalCenter + contentBound.width),
                    bottom: innerHeight - (coverTop + contentBound.height)
                },
                style: styles.topCenter,
            },
            {
                at: 'top-end',
                cover: {
                    left: coverVerticalRight,
                    top: coverTop,
                    right: innerWidth - (coverVerticalRight + contentBound.width),
                    bottom: innerHeight - (coverTop + contentBound.height)
                },
                style: styles.topEnd,
            },
            {
                at: 'left-start',
                cover: {
                    left: coverLeft,
                    top: coverHorizonTop,
                    right: innerWidth - (coverLeft + contentBound.width),
                    bottom: innerHeight - (coverHorizonTop + contentBound.height)
                },
                style: styles.leftStart,
            },
            {
                at: 'left-center',
                cover: {
                    left: coverLeft,
                    top: calcHorizonCenter,
                    right: innerWidth - (coverLeft + contentBound.width),
                    bottom: innerHeight - (calcHorizonCenter + contentBound.height)
                },
                style: styles.leftCenter,
            },
            {
                at: 'left-end',
                cover: {
                    left: coverLeft,
                    top: coverHorizonBottom,
                    right: innerWidth - (coverLeft + contentBound.width),
                    bottom: innerHeight - (coverHorizonBottom + contentBound.height)
                },
                style: styles.leftEnd,
            },
            {
                at: 'right-start',
                cover: {
                    left: innerWidth - (coverRight + contentBound.width),
                    top: coverHorizonTop,
                    right: coverRight,
                    bottom: innerHeight - (coverHorizonTop + contentBound.height)
                },
                style: styles.rightStart,
            },
            {
                at: 'right-center',
                cover: {
                    left: innerWidth - (coverRight + contentBound.width),
                    top: calcHorizonCenter,
                    right: coverRight,
                    bottom: innerHeight - (calcHorizonCenter + contentBound.height)
                },
                style: styles.rightCenter,
            },
            {
                at: 'right-end',
                cover: {
                    left: innerWidth - (coverRight + contentBound.width),
                    top: coverHorizonBottom,
                    right: coverRight,
                    bottom: innerHeight - (coverHorizonBottom + contentBound.height)
                },
                style: styles.rightEnd,
            },
            {
                at: 'bottom-start',
                cover: {
                    left: coverVerticalLeft,
                    top: innerHeight - (coverBottom + contentBound.height),
                    right: innerWidth - (coverVerticalLeft + contentBound.width),
                    bottom: coverBottom
                },
                style: styles.bottomStart,
            },
            {
                at: 'bottom-center',
                cover: {
                    left: calcVerticalCenter,
                    top: innerHeight - (coverBottom + contentBound.height),
                    right: innerWidth - (calcVerticalCenter + contentBound.width),
                    bottom: coverBottom
                },
                style: styles.bottomCenter,
            },
            {
                at: 'bottom-end',
                cover: {
                    left: coverVerticalRight,
                    top: innerHeight - (coverBottom + contentBound.height),
                    right: innerWidth - (coverVerticalRight + contentBound.width),
                    bottom: coverBottom
                },
                style: styles.bottomEnd,
            }
        ];

        let get;

        if (options.placement === 'auto') {
            const compute = pos.map(({cover}) =>
                Math.max(cover.left, cover.right) + Math.max(cover.top, cover.bottom));
            const findIndex = compute.indexOf(Math.min(...compute));
            get = pos[findIndex];
        } else {
            get = pos.filter(val => val.at === options.placement)[0];
        }

        positionStyle = get.style;
    }

    onMount(() => {
        setStyle();
        calculatePosition();
    });

</script>

<svelte:window on:resize={calculatePosition}/>
<div class="overlay" on:click={close}>
    <div class="popover-item"
         bind:this={contentRef}
         style="{style}; {positionStyle}"
    >
        <svelte:component this={options.component.src} {...options.component.props}/>
    </div>
</div>

<style>

    :root {
        --own-overlay-bg: rgba(0, 0, 0, 0.02);

        --own-popover-bg: var(--cds-field);
        --own-popover-border: none;
        --own-popover-border-radius: 5px;
        --own-popover-padding: 0;
    }

    .overlay {
        width: 100vw;
        height: 100vh;

        position: relative;

        background: var(--own-overlay-bg);
        z-index: 9999;
    }

    .popover-item {
        position: absolute;

        background: var(--own-popover-bg, var(--cds-field));
        border: var(--own-popover-border, none);
        border-radius: var(--own-popover-border-radius, 5px);
        padding: var(--own-popover-padding, 0);
        z-index: 9999;
    }

</style>