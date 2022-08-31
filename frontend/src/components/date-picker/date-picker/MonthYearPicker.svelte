<script lang="ts">
    import {onDestroy, onMount} from "svelte";
    import Picker from "../../picker/Picker.svelte";
    import PickerColumn from "../../picker/PickerColumn.svelte";
    import PickerColumnDivider from "../../picker/PickerColumnDivider.svelte";
    import dayjs from "dayjs";

    let componentIsReady: boolean = false;

    export let value: Date = new Date();
    export let minValue: Date;
    export let maxValue: Date;

    if (!minValue) {
        minValue = dayjs(value).add(-50, 'year').toDate();
    }
    if (!maxValue) {
        maxValue = dayjs(value).add(50, 'year').toDate();
    }
    if (maxValue < minValue) {
        throw Error("Max value mast be greater than min value");
    }

    let months: string[] = Array(12)
        .fill(0)
        .map((_, i) => i)
        .map(it => dayjs()
            .set('month', it).format('MMMM')
        );
    let years: string[] = Array(dayjs(maxValue).diff(minValue, 'year') + 1)
        .fill(0)
        .map((_, i) => i)
        .map(it => dayjs(minValue)
            .add(it, 'year').format('YYYY')
        );

    let month: number = value.getMonth();
    let year: number = years.indexOf(String(value.getFullYear()));

    let lastYear: number;

    const lastYearWasMin = (): boolean => Number(years[lastYear]) === minValue.getFullYear();
    const lastYearWasMax = (): boolean => Number(years[lastYear]) === maxValue.getFullYear();

    const changeMonths = () => {
        if (!componentIsReady) return;

        const minYear = Number(years[year]) === minValue.getFullYear();
        const maxYear = Number(years[year]) === maxValue.getFullYear();

        if (minYear && months.length === (12 - minValue.getMonth())) return;
        if (maxYear && months.length === (12 - maxValue.getMonth())) return;
        if (minYear && maxYear
            && months.length === (12 - minValue.getMonth() - maxValue.getMonth())) return;

        if (!minYear && !maxYear && months.length === 12) return;

        months = [];

        const firstMonth = minYear ? minValue.getMonth() : 0;
        const latMonth = maxYear ? maxValue.getMonth() : 11;

        const date = dayjs();
        for (let i = firstMonth; i <= latMonth; i++) {
            months.push(date.set('month', i).format('MMMM'));
        }

        months = months;

        if (minYear) {
            const minValMonth = minValue.getMonth();
            if (month < minValMonth) {
                month = 0;
            } else {
                month -= minValMonth;
            }
        } else if (lastYearWasMin()) {
            month += minValue.getMonth();
        }

        if (maxYear) {
            const maxValMonth = maxValue.getMonth()
            if (month > maxValMonth) {
                month = maxValMonth;
            }
        }
    }

    $:{
        changeMonths();
        lastYear = year;
    }

    onMount(() => {
        componentIsReady = true;
        changeMonths();
    });

    onDestroy(() => {
        const yearNumber = Number(years[year]);

        if (yearNumber === minValue.getFullYear())
            month += minValue.getMonth();

        value.setFullYear(yearNumber, month);
        value = value;
    })

</script>

<div class="time_picker elevation-8">
    <Picker>
        <PickerColumn data={months} bind:selected={month}
                      options={{
                           pickerWidth: 120,
                           pickerUnit: 'px',
                           pickerScrollPadding: 122,
                           pickerHeight: 32
                      }}/>
        <PickerColumnDivider divider={' '}/>
        <PickerColumn data={years} bind:selected={year}/>
    </Picker>
</div>

<style>

    :global(:root) {
        --own-timepicker-height: 240px;
        --own-timepicker-width: 240px;
    }

    .time_picker {
        height: var(--own-timepicker-height);
        width: var(--own-timepicker-width);

        position: relative;
        -webkit-user-select: none;
        user-select: none;
        background: var(--cds-field);
        border-radius: 16px;
    }

</style>