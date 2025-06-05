import React from 'react';
import {useNavigate, useSearchParams} from 'react-router-dom';
import logo from '../assets/trading-logo.svg'
import Button from './Button';

const navItems = [
    {label: 'Home', aria: 'home', path: '/home',},
    {label: 'Trading', aria: 'trade', path: '/trade'},
    {label: 'Middle Office', aria: 'trades', path: '/middle-office'},
    {label: 'Support Team', aria: 'support', path: '/support'},
    {label: 'Administrator', aria: 'admin', path: '/admin'},
];

const Navbar = () => {
    const navigate = useNavigate();
    const [searchParams, setSearchParams] = useSearchParams();

    const handleNavClick = (path: string) => {
        console.log("Navigating to:", path);
        setSearchParams({view: path}); // Update search params
        navigate(path);
    }

    return (
        <>
            <div className="w-full flex justify-center mb-2">
            </div>
            <nav className="bg-white w-full shadow-lg mx-auto py-3 flex items-center rounded-2xl">
                <div className={"justify-start gap-6 flex w-1/6"}>
                    <img src={logo} alt={"logo"} className={"ml-2 w-8 h-8"}/>
                    <div className="font-bold font-sans text-lg">Trading Platform</div>
                </div>
                <div className="inline-flex rounded-xl shadow bg-white p-1 gap-2">
                    {navItems.map((item) => (
                        <Button
                            key={item.aria}
                            variant="primary"
                            size="md"
                            className="px-4 py-2 rounded-lg !text-black bg-transparent hover:bg-indigo-600 font-semibold transition cursor-pointer shadow-none"
                            aria-label={item.aria}
                            onClick={() => handleNavClick(item.path)}
                        >
                            {item.label}
                        </Button>
                    ))}
                </div>

            </nav>
        </>
    );
};

export default Navbar;
