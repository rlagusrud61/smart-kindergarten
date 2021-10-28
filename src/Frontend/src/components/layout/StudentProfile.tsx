import { StudentHistory } from "./StudentHistory";

export function StudentProfile() {

    return (
        <div className="relative p-9">

            {/* Image and name of student  */}
            <div className="p-3 grid grid-cols-3">
                <img className="col-span-1 object-contain transform w-32 rounded-full" alt='kid' src="img/kidlmao.jpg" />
                <div className="py-5 table-cell row-span-2 col-span-2">
                    <h1 className="align-middle font-bold text-4xl">X Æ A-12</h1>
                    <br></br>
                    <h3 className="align-middle text-2xl">Class 1</h3>
                </div>
            </div>

            {/* Heart Rate */}

            <div className="p-8 grid grid-cols-3">
            <svg className="col-span-1" xmlns="http://www.w3.org/2000/svg" width="60" height="60" viewBox="0 0 20 20">
                <path d="M14.75 1A5.24 5.24 0 0 0 10 4 5.24 5.24 0 0 0 0 6.25C0 11.75 10 19 10 19s10-7.25 10-12.75A5.25 5.25 0 0 0 14.75 1z" />
            </svg>
            <h1 className="col-span-2 py-4 text-2xl"> 90 BPM</h1>
            </div>


            <StudentHistory></StudentHistory>
        </div>
    );
}