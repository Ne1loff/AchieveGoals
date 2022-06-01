<script lang="ts">
    import {createEventDispatcher} from 'svelte';
    import {datesEqual, datesLessEqual, getCalendar, getWeekdays,} from './datetime-utils.js';
    import {Button} from "attractions";

    export let weekdaysClass: string = '';
    export let weekClass: string = '';
    export let dayClass: string = '';
    export let locale: string = undefined;
    export let firstWeekday: number = 1;
    export let month: number;
    export let year: number;
    export let selected: Date = null;
    export let selectionStart: Date = null;
    export let selectionEnd: Date = null;
    export let disabledDates: Array<Date | { start?: Date, end?: Date }> = [];

    const weekdays = getWeekdays(locale, firstWeekday);
    const today = new Date();

    function computeTitle(day) {
        if (datesEqual(day.value, today)) {
            if (day.disabled) {
                return 'Today, not available';
            } else {
                return 'Today';
            }
        }

        if (day.disabled) {
            return 'Not available';
        }

        return null;
    }

    const dayNumberFormatter = Intl.DateTimeFormat(locale, {day: 'numeric'});
    const dispatch = createEventDispatcher();
</script>

<div class='weekdays {weekdaysClass}'>
    {#each weekdays as dayName (dayName)}
        <span class="weekday">{dayName}</span>
    {/each}
</div>
{#each getCalendar(month, year, firstWeekday, disabledDates) as week}
    <div class='week {weekClass}'>
        {#each week as day}
            <div
                    class='day {dayClass}'
                    class:today={datesEqual(day.value, today)}
                    class:outside={day.outside}
                    class:selected={
                        datesEqual(day.value, selectionStart) ||
                        datesEqual(day.value, selectionEnd) ||
                        datesEqual(day.value, selected)
                    }
                    class:start={datesEqual(day.value, selectionStart)}
                    class:end={datesEqual(day.value, selectionEnd)}
                    class:in-range={datesLessEqual(selectionStart, day.value) && datesLessEqual(day.value, selectionEnd)}
                    class:disabled={day.disabled}>
                <Button
                        title={computeTitle(day)}
                        on:click={e => {
                            e.detail.nativeEvent.stopPropagation();
                            selected.setFullYear(day.value.getFullYear(), day.value.getMonth(), day.value.getDate());
                            selected = selected;
                            dispatch('day-select', day.value);
                        }}
                        disabled={day.disabled}>
                    {dayNumberFormatter.format(day.value)}
                </Button>
            </div>
        {/each}
    </div>
{/each}


<style lang="scss">
  @use 'sass:color';
  @use 'sass:math';
  @use 'node_modules/attractions/_variables' as vars;

  $day-size: 2.25rem;
  $day-gap: 0.0625rem;
  $side-padding: 0;

  .weekdays {
    display: flex;
    font-size: 0.85em;
    padding: .5em .25rem;

    > .weekday {
      text-align: center;
      width: percentage(math.div(1, 7));
    }
  }

  .week {
    display: flex;
    padding: 0 .25rem;

    .day {
      align-items: center;
      display: flex;
      justify-content: center;
      position: relative;
      width: percentage(math.div(1, 7));

      &:last-child {
        padding-right: $side-padding;
      }

      &:first-child {
        padding-left: $side-padding;
      }

      > :global .btn {
        color: var(--cds-text-01);
        font-size: 0.9em;
        font-weight: vars.$regular-font-weight;
        height: $day-size;
        justify-content: center;
        margin: $day-gap;
        width: $day-size;
        z-index: 2;

        &:hover {
          background-color: var(--cds-border-interactive);
          color: var(--cds-text-04);
        }
      }


      &.today > :global .btn {
        color: var(--cds-inverse-link);

        &:hover {
          color: var(--cds-text-04);
        }
      }

      &.selected > :global .btn {
        background-color: var(--cds-button-primary-active) !important;
        color: var(--cds-text-04) !important;
      }

      &.outside > :global .btn {
        color: var(--cds-text-03);
      }

      &.disabled > :global .btn {
        color: var(--cds-text-error);
        position: relative;

        &:hover {
          background: inherit;
        }

        &::before {
          background: var(--cds-text-error);
          content: '';
          height: 1px;
          left: 50%;
          position: absolute;
          top: 50%;
          transform: translateX(-50%) rotate(-30deg);
          width: 50%;
        }
      }

      &.in-range::before {
        background-color: var(--cds-border-interactive);
        content: '';
        height: $day-size;
        left: 0;
        position: absolute;
        right: 0;
        top: $day-gap;
        z-index: 1;
      }

      &.outside.selected > :global .btn {
        color: var(--cds-text-disabled);
      }

      &.start {
        &::before {
          left: unset;
          right: 0;
          width: math.div($day-size, 2) + $day-gap;
        }

        &:last-child::before {
          width: math.div($day-size, 2) + $day-gap + $side-padding;
        }
      }

      &.end {
        &::before {
          left: 0;
          right: unset;
          width: math.div($day-size, 2) + $day-gap;
        }
      }

      &.start:last-child,
      &.end:first-child {
        &::before {
          width: math.div($day-size, 2) + $day-gap + $side-padding;
        }
      }

      &.start.end::before {
        width: 0;
      }
    }
  }
</style>