export const StudentHistory = () => {
    return (

        <div className="bg-gray-50 shadow-xl md:overflow-scroll h-96 overscroll-x-none overscroll-y-auto rounded-lg">

            <h1 className=" text-gray-700 py-5 px-5 font-bold text-2xl">History</h1>

            <div className="p-5 grid grid-cols-4">
                <img className="px-4 col-span-1 object-contain transform w-32" src="img/fallingdown.png" alt="Falling Down"></img>
                <h1 className="text-xl align-middle row-span-2 col-span-3">Falling down</h1>
            </div>

            <div className="p-5 grid grid-cols-4">
                <img className="px-4 col-span-1 object-contain transform w-32" src="img/fallingdown.png" alt="Falling Down"></img>
                <h1 className="text-xl align-middle row-span-2 col-span-3">Falling down</h1>
            </div>
            <div className="p-5 grid grid-cols-4">
                <img className="px-4 col-span-1 object-contain transform w-32" src="img/fallingdown.png" alt="Falling Down"></img>
                <h1 className="text-xl align-middle row-span-2 col-span-3">Falling down</h1>
            </div>
        </div>
    );
}