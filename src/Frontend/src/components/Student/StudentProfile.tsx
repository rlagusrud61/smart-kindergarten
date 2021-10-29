import {StudentHistory} from "./StudentHistory";
import {Student} from "../../clients/rest/ApiClient";
import {HeartFilled} from "@fluentui/react-icons";

interface StudentProfileProps {
    student: Student;
}

export function StudentProfile({student}: StudentProfileProps) {
    return (
        <>
            {/* Image and name of student  */}
            <ProfileDetails student={student}/>
            <StudentHistory student={student}/>
        </>
    );
}

export const ProfileDetails = ({student}: StudentProfileProps) => {
    return (
        <div className="bg-white shadow-lg rounded-xl p-3">
            <div className="p-4 grid grid-cols-3">
                <img className="col-span-1 object-contain transform w-32 rounded-full" alt='kid' src={ImagePathForStudent(student)}/>
                <div className="py-5 col-span-2">
                    <h1 className="text-gray-700 align-middle font-bold text-3xl">{student.name}</h1>
                    <br/>
                    <h3 className="align-middle text-xl">Age : {student.age}</h3>
                </div>
            </div>

            <div className="p-4 grid grid-cols-3">
                <HeartFilled className="self-center" color="#BBB" fontSize={"4.5rem"}/>
                <h1 className="col-span-2 self-center text-2xl">90 BPM</h1>
            </div>
        </div>)
}

export const ImagePathForStudent = (student:Student) : string => {
    switch (student.name){
        case "Michiel":
            return "img/michiel.png"
        case "Hyunk":
            return "img/kim.jpg"
        default:
            return "img/kidlmao.jpg";
    }
}
