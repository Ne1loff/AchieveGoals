<script lang="ts">

    import MaskInput from "svelte-input-mask";
    import dayjs from "dayjs";

    export let value: Date = undefined;

    interface InputState {
        unmaskedValue: string,
        maskedValue: string,
        visibleValue: string
    }

    interface Detail {
        element: HTMLElement,
        inputState: InputState
    }

    let maskString = "HH:MM";
    let mask = "00:00";

    let error: boolean = false;

    const updateFromValue = (time?: Date): string => {
        return time ? dayjs(time).format('HH:mm') : interValue;
    }

    let interValue: string = '';
    $:interValue = updateFromValue(value);

    const handleChange = ({detail}: { detail: Detail }) => {
        error = false;
        const strArr = String(detail.inputState.maskedValue).split('');

        if (parseInt(strArr[0], 10) > 2)
            strArr[0] = '2';
        if (parseInt(strArr[0], 10) === 2 && parseInt(strArr[1], 10) > 3)
            strArr[1] = '3';
        if (parseInt(strArr[3], 10) > 5)
            strArr[3] = '5';

        interValue = strArr.join('');

        if (detail.inputState.visibleValue.length === maskString.length) updateValue();
    };

    const updateValue = () => {
        const timeArr = interValue.split(':').map(it => Number(it));

        let time: Date;

        time = dayjs(value).set('hour', timeArr[0]).set('minute', timeArr[1]).toDate();
        if (time instanceof Date && !isNaN(time.valueOf()))
            value = time;
        else
            error = true;
    }

</script>

<MaskInput value={interValue} alwaysShowMask {maskString} {mask}
           on:change={handleChange}
           on:blur={updateValue}
           class="time-input {error ? 'error' : ''}"
/>

<style>

    :global(.time-input) {
        margin: 0;
        height: 40px;
        padding: 10px;
        width: 80px;
        border: none;
        border-radius: .25rem 0 0 .25rem;
        border-right: 1px solid var(--cds-button-secondary-hover);
        background-color: var(--cds-interactive-02);
        color: var(--cds-text-04);
    }

    :global(.time-input:focus-visible) {
        outline: var(--cds-focus) solid 1px;
        z-index: 1;
    }

    :global(.time-input.error) {
        border: 2px solid var(--cds-support-error);
        color: var(--cds-text-error);
    }

</style>