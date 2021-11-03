import React, {Fragment} from "react";
import {Dismiss12Filled, LineHorizontal3Filled} from "@fluentui/react-icons";
import {Disclosure, Menu, Transition} from '@headlessui/react';
import {Link} from "react-router-dom";
import {Toggle} from "../theme/Toggle";

function classNames(...classes:any) {
    return classes.filter(Boolean).join(' ')
}

const user = {
    name: 'Random Teacher LMAO',
    email: 'teacher@example.com',
    imageUrl:
        'img/kidlmao.jpg',
}
const navigation = [
    { name: 'Dashboard', href: '/', current: true },
    { name: 'History', href: '/History', current: false },

]

const userNavigation = [
    { name: 'Your Profile', href: '#' },
    { name: 'Settings', href: '#' },
    { name: 'Sign out', href: '#' },
]
export const TopNav = () => {
    return (

        <div className="min-h-full">
            <Disclosure as="nav" className="bg-gradient-to-r from-purple-400 via-pink-500 to-red-500">
                {({ open }) => (
                    <>
                        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
                            <div className="flex items-center justify-between h-16">
                                <div className="flex items-center">
                                    <div className="flex-shrink-0">
                                       <h1 className="text-sm text-white rand-logo text-2xl font-bold px-5">Smart Kindergarten &trade;</h1>
                                    </div>
                                    <div className="hidden md:block">
                                        <div className="ml-10 flex items-baseline space-x-4">
                                            {navigation.map((item) => (
                                                <a
                                                    key={item.name}
                                                    className={classNames(
                                                        item.current
                                                            ? 'bg-white text-gray-800 bg-opacity-90 dark:bg-gray-600 dark:bg-opacity-90 dark:text-white'
                                                            : 'text-white hover:bg-gray-700 hover:text-white hover:bg-opacity-70',
                                                        'px-3 py-2 rounded-md text-sm font-medium'
                                                    )}
                                                    aria-current={item.current ? 'page' : undefined}
                                                ><Link to={item.href}>
                                                    {item.name}
                                                </Link>
                                                </a>
                                            ))}
                                        </div>
                                    </div>
                                </div>
                                <div className="hidden md:block">
                                    <div className="ml-4 flex items-center md:ml-6">
                                        <Toggle/>
                                        {/* Profile dropdown */}
                                        <Menu as="div" className="z-10 ml-3 relative">
                                            <div className="flex flex-row">
                                                <Menu.Button className="max-w-xs bg-gray-800 rounded-full flex items-center text-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-800 focus:ring-white">
                                                    <span className="sr-only">Open user menu</span>
                                                    <img className="h-8 w-8 rounded-full" src={user.imageUrl} alt="" />
                                                </Menu.Button>

                                            </div>
                                            <Transition
                                                as={Fragment}
                                                enter="transition ease-out duration-100"
                                                enterFrom="transform opacity-0 scale-95"
                                                enterTo="transform opacity-100 scale-100"
                                                leave="transition ease-in duration-75"
                                                leaveFrom="transform opacity-100 scale-100"
                                                leaveTo="transform opacity-0 scale-95"
                                            >
                                                <Menu.Items className="origin-top-right absolute right-0 mt-2 w-48 rounded-md shadow-lg py-1 bg-white ring-1 ring-black ring-opacity-5 focus:outline-none">
                                                    {userNavigation.map((item) => (
                                                        <Menu.Item key={item.name}>
                                                            {({ active }) => (
                                                                <a
                                                                    href={item.href}
                                                                    className={classNames(
                                                                        active ? 'bg-gray-100' : '',
                                                                        'block px-4 py-2 text-sm text-gray-700'
                                                                    )}
                                                                ><Link to={item.href}>
                                                                    {item.name}
                                                                </Link>
                                                                </a>
                                                            )}
                                                        </Menu.Item>
                                                    ))}
                                                </Menu.Items>
                                            </Transition>
                                        </Menu>
                                    </div>
                                </div>
                                <div className="-mr-2 flex md:hidden">
                                    {/* Mobile menu button */}
                                    <Disclosure.Button className="bg-white bg-opacity-90 items-center justify-center p-2 rounded-md text-gray-400 hover:text-white hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-offset-gray-800 focus:ring-white">
                                        <span className="sr-only">Open main menu</span>
                                        {open ? (
                                            <Dismiss12Filled className="block h-6 w-6" aria-hidden="true" />
                                        ) : (
                                            <LineHorizontal3Filled className="block h-6 w-6" aria-hidden="true" />
                                        )}
                                    </Disclosure.Button>
                                </div>
                            </div>
                        </div>

                        <Disclosure.Panel className="md:hidden">
                            <div className="px-2 pt-2 pb-3 space-y-1 sm:px-3">
                                {navigation.map((item) => (
                                    <Disclosure.Button
                                        key={item.name}
                                        as="a"
                                        href={item.href}
                                        className={classNames(
                                            item.current ? 'bg-white text-gray-800 bg-opacity-80' : 'text-white hover:bg-white hover:text-gray-800',
                                            'block px-3 py-2 rounded-md text-base font-medium'
                                        )}
                                        aria-current={item.current ? 'page' : undefined}
                                    >
                                        {item.name}
                                    </Disclosure.Button>
                                ))}
                            </div>
                            <div className="pt-4 pb-3 border-t border-white">
                                <div className="flex items-center px-5">
                                    <div className="flex-shrink-0">
                                        <img className="h-10 w-10 rounded-full" src={user.imageUrl} alt="" />
                                    </div>
                                    <div className="ml-3">
                                        <div className="text-base font-medium leading-none text-white">{user.name}</div>
                                        <div className="text-sm font-medium leading-none text-white">{user.email}</div>
                                    </div>
                                    <div className="ml-3 flex-shrink-0">
                                    <Toggle/>
                                    </div>
                                </div>
                                <div className="mt-3 px-2 space-y-1">
                                    {userNavigation.map((item) => (
                                        <Disclosure.Button
                                            key={item.name}
                                            as="a"
                                            href={item.href}
                                            className="block px-3 py-2 rounded-md text-base font-medium text-white hover:text-white hover:bg-gray-700"
                                        >
                                            {item.name}
                                        </Disclosure.Button>
                                    ))}
                                </div>
                            </div>
                        </Disclosure.Panel>
                    </>
                )}
            </Disclosure>


        </div>

    // <div className="z-10 fixed w-full h-16 bg-gradient-to-r from-purple-400 via-pink-500 to-red-500 text-white justify-between flex flex-row items-center">
    //     <div className="md:text-xs brand-logo text-2xl font-bold px-5">Smart Kindergarten &trade;</div>
    //     <ul className="flex flex-row px-5 justify-center items-center">
    //         <li className="px-5 "><Link to="/">Home</Link></li>
    //         <li className="px-5 "><Link to="/History">History</Link></li>
    //         <li className="px-5">
    //             <Toggle/>
    //         </li>
    //     </ul>
    // </div>
        );
}
