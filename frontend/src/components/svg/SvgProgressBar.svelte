<script lang="ts">

    export let value: number = 0;
    export let innerRadius: number = 90;
    export let outerRadius: number = 100;
    export let size: number = 48;

    innerRadius = (size / 2) * innerRadius / 100;
    outerRadius = (size / 2) * outerRadius / 100;

    const MAX_PERCENT: number = 100;
    const MIN_PERCENT: number = 0;

    let angle: number;
    let x, y: number;
    let halfSize: number = size / 2;


    const toRad = (deg: number) => deg / 180 * Math.PI;
    const getCircumferenceLength = (radius: number) => 2 * Math.PI * radius;

    $: {
        value = Math.max(Math.min(value, MAX_PERCENT), MIN_PERCENT);
        const angleDeg = 360 - 360 * ((MAX_PERCENT - value) / 100)
        angle = toRad(angleDeg);
        x = innerRadius * Math.cos(angle) + halfSize;
        y = innerRadius * Math.sin(angle) + halfSize;
    }

</script>

<div class="progress-bar" class:filled={value === 100}>
    <svg width={size} height={size}>
        <g transform="rotate(-90 {halfSize} {halfSize})">
            <circle cx={halfSize} cy={halfSize} r={outerRadius}
                    stroke="#673E93"
                    stroke-dasharray={getCircumferenceLength(outerRadius)}
                    stroke-dashoffset={0} fill="none"/>
            {#if value > 0 && value < 100}
                <path d="M {halfSize} {halfSize} H {halfSize + innerRadius} A {innerRadius},{innerRadius} 0 {value > 50 ? 1 : 0}, 1 {x}, {y} z"/>
            {:else if value === 100}
                <circle id="filled" cx={halfSize} cy={halfSize} r={innerRadius}
                        stroke="#673E93"
                        stroke-dasharray={getCircumferenceLength(innerRadius)}
                        stroke-dashoffset={0} fill="#666"/>
            {/if}
        </g>
    </svg>
</div>

<style>

    :root {
        --progress-bar-inner-color: #ff9f1e;
        --progress-bar-outer-color: #666;
        --progress-bar-outer-stroke-width: .5rem;
    }

    .progress-bar {
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .progress-bar circle {
        stroke: var(--progress-bar-outer-color);
        stroke-width: var(--progress-bar-outer-stroke-width);
    }

    .progress-bar.filled #filled {
        stroke-width: 0;
    }

    .progress-bar path, .progress-bar #filled {
        fill: var(--progress-bar-inner-color);
    }

</style>