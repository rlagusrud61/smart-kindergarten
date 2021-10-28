import { StudentHistory } from "./StudentHistory";

interface StudentProfileProps{
    student: string;
}
export function StudentProfile({student}:StudentProfileProps) {
    return (
        <div className="relative p-9">

            {/* Image and name of student  */}
            <div className="bg-white shadow-lg rounded-xl p-3">
                <div className="p-4 grid grid-cols-3">
                    <img className="col-span-1 object-contain transform w-32 rounded-full" alt='kid' src="img/kidlmao.jpg" />
                    <div className="py-5 table-cell col-span-2">
                        <h1 className="text-gray-700 align-middle font-bold text-3xl">{student}</h1>
                        <br></br>
                        <h3 className="align-middle text-xl">Age : 5</h3>
                    </div>
                </div>

                <div className="p-4 grid grid-cols-3">
                <img className="col-span-1 transform w-24 object-contain px-5" src="img/heart.png"></img>
                    <h1 className="col-span-2 align-middle text-2xl"> 90 BPM</h1>

                </div>
                {/* Heart Rate */}


            </div>
            <br></br>
            <StudentHistory></StudentHistory>
        </div>
    );
}