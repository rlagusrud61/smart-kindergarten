export const StudentList = () => {
    return (
        <div className="overflow-auto px-5">
            {/* Search Bar */}
            <div className="py-4">
                <div className="h-14 bg-white flex items-center rounded-full shadow-xl">
                    <input className="rounded-l-full w-full py-4 px-6 text-gray-700 leading-tight focus:outline-none" id="search" type="text" placeholder="Search"></input>
                    <div className="p-4">
                        <button className="bg-purple-500 text-white rounded-full p-2 hover:bg-purple-400 focus:outline-none w-10 h-10 flex items-center justify-center">
                            <svg fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" viewBox="0 0 24 24" className="w-6 h-6">
                                <path d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                            </svg>
                        </button>
                    </div>
                </div>
            </div>


            {/* List of Students */}
            <div className="bg-gray-50 rounded-xl">

                {/* Random Student #1 */}
                <div className="p-3 grid grid-cols-3">
                    <img className="col-span-1 object-contain transform w-16 rounded-full" alt='kid' src="img/kidlmao.jpg" />
                    <div className="table align-middle row-span-2 col-span-2">
                        <h1 className="table-cell align-middle text-justify text-lg">X Æ A-12</h1>
                    </div>
                </div>



                {/* Random Student #2 */}
                <div className="p-3 grid grid-cols-3">
                    <img className="col-span-1 object-contain transform w-16 rounded-full" alt='kid' src="img/kidlmao.jpg" />
                    <div className="table align-middle row-span-2 col-span-2">
                        <h1 className="table-cell align-middle text-justify text-lg">Another student lmao</h1>
                    </div>
                </div>
            </div>
        </div>
    );
}