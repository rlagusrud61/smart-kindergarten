import {useContext, useEffect, useState} from "react";
import {ActivityContext, GetUrgentEventMessage} from "../../context/ActivityContext";
import {EventHistory, Student, UrgentEvent} from "../../clients/rest/ApiClient";
import TimeAgo from "react-timeago";

export const StudentHistory = ({student}:{student:Student}) => {

    const activityContext = useContext(ActivityContext);
    const [history, setHistory] = useState<EventHistory[]>([{
        student: {name: student.name, age: student.age,},
        event: UrgentEvent.Crying,
        date: new Date("2021-10-29T06:20:54+00:00")
    }]);

    useEffect(() => {
        history[0].student = student;
        setHistory([...history]);
    },[student])

    useEffect(() => {
        let hub = activityContext?.hub;
        hub?.urgentEventEvent.addHandler(addHistory);

        function addHistory(event: EventHistory) {
            history.push(event)
            setHistory([...history])
        }

        return () => {
            hub?.urgentEventEvent.removeHandler(addHistory);
        }
    }, [activityContext])

    return (
        <div className="bg-white shadow-lg rounded-xl flex flex-col overflow-y-hidden">
            <h1 className="text-gray-700 py-5 px-5 font-bold text-2xl">History</h1>
            <div className="overflow-y-auto scrollbar">
                {[...history].filter(n => n.student?.id === student.id).reverse().map(history => (
                    <HistoryEntry key={"dt-" + history.date!} history={history}/>
                ))}
            </div>
        </div>
    );
}

const HistoryEntry = ({history}: { history: EventHistory }) => {

    return (
        <li className="p-5 grid grid-cols-4">
            <img className="px-4 col-span-1 object-contain transform w-24" src="img/fallingdown.png" alt={history.event}/>
            <h1 className="text-xl row-span-2 col-span-2">{GetUrgentEventMessage(history.student!, history.event!)}</h1>
            <TimeAgo className="row-span-2 col-span-1 text-right pr-4" date={history.date!}/>
        </li>)
}
