import React, {useEffect, useState} from 'react';
import '../../styles/tailwind.css'
import {Student, StudentsClient} from "../../clients/rest/ApiClient";
import {StudentProfile} from '../Student/StudentProfile';
import {StudentList} from '../Student/StudentList';
import FadeIn from "react-fade-in";

function Home() {

    const [selectedStudent, setSelectedStudent] = useState<Student>();
    const [students, setStudents] = useState<Student[]>([]);
    const [theme, setTheme] = useState('light');


    function toggleTheme(){
        if (theme === 'light'){
            setTheme('dark');
        }else{
            setTheme('light');
        }
    }

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
        <main className="relative h-screen overflow-y-hidden pt-16">
<FadeIn>
            <div className="max-w-7xl mx-auto sm:px-6 lg:px-8 h-full">
                <div className="grid grid-cols-3 grid-rows-2 gap-20 px-4 py-12 sm:px-0 h-full max-h-full overflow-y-hidden" style={{gridTemplateRows:"4rem 1fr",gridRowGap:0}}>
                    <h1 className="text-center text-4xl font-bold text-gray-700 dark:text-white">Students</h1>
                    <h1 className="text-center text-4xl font-bold text-gray-700 col-span-2 dark:text-white">Student Profile</h1>

                    <div className="flex flex-col h-full max-h-full">
                        <div className="dark:bg-gray-700 bg-gray-100 shadow-lg rounded-lg flex-grow">
                            <StudentList students={students} setSelectedStudent={setSelectedStudent}/>
                        </div>
                    </div>
                    <div className="dark:bg-gray-700 col-span-2 h-full max-h-full overflow-y-hidden bg-gray-100 shadow-lg rounded-lg p-7 flex flex-col gap-7">
                        {selectedStudent && (<StudentProfile student={selectedStudent}/>)}
                    </div>
                </div>
            </div>
</FadeIn>
        </main>
    );
}

export default Home;
