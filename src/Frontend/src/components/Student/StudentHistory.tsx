export const StudentHistory = () => {


    return (
        <div className="bg-white shadow-lg rounded-xl flex flex-col overflow-y-hidden">
            <h1 className="text-gray-700 py-5 px-5 font-bold text-2xl">History</h1>
            <div className="overflow-y-auto scrollbar">
                <HistoryEntry/>
                <HistoryEntry/>
                <HistoryEntry/>
                <HistoryEntry/>
                <HistoryEntry/>
                <HistoryEntry/>
                <HistoryEntry/>
                <HistoryEntry/>
                <HistoryEntry/>
                <HistoryEntry/>
                <HistoryEntry/>
                <HistoryEntry/>
                <HistoryEntry/>
                <HistoryEntry/>
                <HistoryEntry/>
                <HistoryEntry/>
                <HistoryEntry/>
            </div>
        </div>
    );
}

const HistoryEntry = () => {

    return (
        <li className="p-5 grid grid-cols-4">
            <img className="px-4 col-span-1 object-contain transform w-32" src="img/fallingdown.png" alt="Falling Down"/>
            <h1 className="text-xl align-middle row-span-2 col-span-3">Falling down</h1>
        </li>)
}
