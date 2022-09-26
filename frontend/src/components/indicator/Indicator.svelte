<script lang="ts">

    export let count: number = 2;
    export let colors: string[] = ['#FF0000', '#00FF00'];
    export let secondary: string = 'var(--cds-button-secondary)'
    export let steps: string[] = [];
    export let singleText: boolean;
    export let size: { h: string, w?: string } = {h: '3rem'};
    export let radius: string = '50%';

    export let currentStep: number;

    const list = Array(count).fill(0);
</script>

<div class="steps">

    <div class="steps-container"
         style:--radius={radius}
         style="--bg-secondary: {secondary}">
        {#each list as step, index}
            <div class="step-container">
                <div class="step"
                     class:bg-secondary={index > currentStep}
                     style:height={size.h}
                     style:width={size.w ?? size.h}
                     style="background: {colors[currentStep]}">
                </div>
                {#if !singleText && steps.length !== 0}
                    <div class="step-text">
                        {steps[currentStep]}
                    </div>
                {/if}
            </div>
        {/each}
    </div>
    {#if singleText && steps.length !== 0}
        <div class="steps-text">
            {steps[currentStep]}
        </div>
    {/if}
</div>


<style>

    .steps {
        display: flex;
        flex-direction: column;
        justify-items: center;
    }

    .steps-container {
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .step-container:not(:first-child) .step {
        border-left: 1px solid var(--cds-border-strong);
    }

    .step-container:first-child .step {
        border-radius: var(--radius, 50%) 0 0 var(--radius, 50%);
    }

    .step-container:last-child .step {
        border-radius: 0 var(--radius, 50%) var(--radius, 50%) 0;
    }

    .step-container {
        height: 100%;
        flex-grow: 1;
        align-items: center;
        justify-items: center;
    }

    .step-text {
        margin-top: .25rem;
    }

    .step.bg-secondary {
        background-color: var(--bg-secondary, #bbbbc0) !important;
    }

</style>