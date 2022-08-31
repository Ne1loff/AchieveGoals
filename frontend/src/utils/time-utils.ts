import dayjs from "dayjs";

interface DateProps {
    year?: number,
    month?: number,
    day?: number,
    hour?: number,
    minute?: number,
    second?: number
}

const getDate = (props: DateProps): Date => {
    const date = dayjs();
    if (props.year) date.set('year', props.year);
    if (props.month) date.set('month', props.month);
    if (props.day) date.set('day', props.day);
    if (props.hour) date.set('hour', props.hour);
    if (props.minute) date.set('minute', props.minute);
    if (props.second) date.set('second', props.second);

    return date.toDate();
}


const getDateTodayLastMin = () => getDate({hour: 23, minute: 59, second: 59});

export {getDateTodayLastMin}