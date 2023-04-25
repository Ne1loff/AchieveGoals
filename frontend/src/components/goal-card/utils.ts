import dayjs from "dayjs";

const getColorFromDayDiff = (date: Date) => {
    const set0 = (dayjsDate: dayjs.Dayjs): dayjs.Dayjs => dayjsDate
        .set('hour', 0).set('minute', 0).set('second', 0).set('millisecond', 0);

    const start = set0(dayjs(date));
    const end = set0(dayjs());
    const diff = start.diff(end, 'day');

    if (diff < 0) return "var(--cds-text-error)";
    if (diff === 0) return "var(--cds-support-success)";
    if (diff === 1) return "var(--cds-support-warning)";
    if (start.day() === 0 || start.day() === 6) return "var(--cds-support-info)";
    if (diff > 1 && diff <= 7) return "var(--own-next-week-color)";
    if (diff >= 8) return "var(--cds-disabled-03)";
}

export {getColorFromDayDiff};