import {StudentHistory} from "./StudentHistory";
import {Activity, Student, StudentsClient, VocalActivity} from "../../clients/rest/ApiClient";
import {HeartFilled} from "@fluentui/react-icons";
import React, {useEffect, useState} from "react";
import {useHub} from "../../Hooks/HubHooks";
import {StudentHub} from "../../clients/signalr/StudentHub";

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

    const hub = useHub(StudentHub, [student], student.id)
    const [currentActivity, setCurrentActivity] = useState<Activity>()
    const [currentVocalActivity, setCurrentVocalActivity] = useState<VocalActivity>()

    useEffect(() => {
        hub?.onActivityUpdate.addHandler((activity:Activity) => {
            setCurrentActivity(activity);
        });

        hub?.onVocalActivityUpdate.addHandler((activity:VocalActivity) => {
            setCurrentVocalActivity(activity);
        });

        // Let's just forget about our cleanup function for a sec
    }, [hub])

    useEffect(() => {
        setCurrentActivity(undefined);
        setCurrentVocalActivity(undefined);
        if (!student.deviceHardwareAddress) return;
        new StudentsClient().activityGET(student.deviceHardwareAddress).then(act => setCurrentActivity(act))
            .catch(() => console.warn("Failed to get student's activity"))
        new StudentsClient().vocalActivityGET(student.deviceHardwareAddress).then(act => setCurrentVocalActivity(act))
            .catch(() => console.warn("Failed to get student's activity"))
    },[student])

    return (
        <div>

            <h1 className="mb-6 text-center text-4xl font-bold text-gray-700 dark:text-white">Student Profile</h1>
            <div className="md:w-full bg-white dark:bg-gray-500 shadow-lg rounded-xl p-3">
                <div className="p-4 grid grid-cols-3">
                    <img className="col-span-1 object-contain transform w-32 rounded-full" alt='kid' src={ImagePathForStudent(student)}/>
                    <div className="py-5 col-span-2">
                        <h1 className="text-gray-700 dark:text-white align-middle font-bold text-3xl mb-1">{student.name}</h1>
                        {/* TODO this definitely needs styling but like 😴😴 */}
                        <h3 className="dark:text-white align-middle text-xl">Age : <b className="text-gray-600">{student.age}</b></h3>

                    </div>

                    <div>
                        <h3 className="dark:text-white align-middle text-xl">Current Activity
                            : <b className="text-gray-600"> {currentActivity}</b></h3>
                        <h3 className="dark:text-white align-middle text-xl">Current 🎙️🎶 Activity
                            : <b className="text-gray-600">{currentVocalActivity}</b></h3>
                    </div>
                </div>

                {/*<div className="p-4 grid grid-cols-3">*/}
                {/*    <HeartFilled className="dark:text-white self-center" color="#BBB" fontSize={"4.5rem"}/>*/}
                {/*    <h1 className="dark:text-white col-span-2 self-center text-2xl">90 BPM</h1>*/}
                {/*</div>*/}
            </div>
        </div>)
}

export const ImagePathForStudent = (student: Student): string => {
    switch (student.name) {
        case "Michiel":
            return "img/michiel.png"
        case "Hyunk":
            return "img/kim.jpg"
        default:
            return "img/kidlmao.jpg";
    }
}
