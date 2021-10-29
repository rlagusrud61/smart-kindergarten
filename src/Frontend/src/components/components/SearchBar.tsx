export function SearchBar(props: any) {

    return (
        <div>
            {/* Search Bar */}
            <div className="py-7">
                <div className="h-14 bg-white flex items-center rounded-full shadow-xl">
                    <input onChange={(e) => {
                        props.setSearchQuery(e.currentTarget.value)
                    }} key="search" className="rounded-l-full w-full py-4 px-6 text-gray-700 leading-tight focus:outline-none" id="search" type="text" placeholder="Search" value={props.searchQuery}/>
                    <div className="p-4">
                        <button className="bg-purple-500 text-white rounded-full p-2 hover:bg-purple-400 focus:outline-none w-10 h-10 flex items-center justify-center">
                            <svg fill="none" stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" viewBox="0 0 24 24" className="w-6 h-6">
                                <path d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                            </svg>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}
