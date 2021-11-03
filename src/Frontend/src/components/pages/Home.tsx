import React, {useEffect, useState} from 'react';
import '../../styles/tailwind.css'
import {Student, StudentsClient} from "../../clients/rest/ApiClient";
import {StudentProfile} from '../Student/StudentProfile';
import {StudentList} from '../Student/StudentList';
import FadeIn from "react-fade-in";

function Home() {

    const [selectedStudent, setSelectedStudent] = useState<Student>();
    const [students, setStudents] = useState<Student[]>([]);


    useEffect(() => {
        if (localStorage.theme === 'dark' || (!('theme' in localStorage) && window.matchMedia('(prefers-color-scheme: dark)').matches)){
            document.documentElement.classList.add('dark')
        } else {
            document.documentElement.classList.remove('dark')
        }
        new StudentsClient().students().then((students) => {
            setStudents(students)
            setSelectedStudent(students[0])

        })
    }, [])

    return (
            // the scrollbar is shit on Edge. Clearly, Chrome is superior.
        <main className="relative h-screen">
<FadeIn>
            <div className="flex flex-col md:flex-row max-w-7xl mx-auto sm:px-6 lg:px-8 h-full">
                    <div className="h-full max-h-full md:flex-col">
                        <div className="m-4 dark:bg-gray-700 bg-gray-100 shadow-lg rounded-lg">
                            <StudentList students={students} setSelectedStudent={setSelectedStudent}/>
                        </div>
                    </div>
                    <div className="m-4 dark:bg-gray-700 h-full max-h-full overflow-y-hidden bg-gray-100 shadow-lg rounded-lg p-7 flex-grow flex flex-col gap-7">
                        {selectedStudent && (<StudentProfile student={selectedStudent}/>)}
                    </div>
            </div>
</FadeIn>
        </main>
    );
}

export default Home;
