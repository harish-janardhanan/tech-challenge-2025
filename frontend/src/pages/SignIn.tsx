import React, { useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import avatar from '../assets/avatar.svg'
import Button from '../components/Button';
import Input from '../components/Input';
import userStore from '../stores/userStore';

const SignIn = () => {
    const emailRef = useRef<HTMLInputElement>(null);
    const navigate = useNavigate();

    const handleSignIn = (e: React.FormEvent) => {
        e.preventDefault();
        const email = emailRef.current?.value || '';
        userStore.setUser(email);
        // userStore.authenticated will be set to true in setUser
        navigate('/main');
    };

    return (
        <div className="relative min-h-screen  w-fit flex items-center justify-center overflow-hidden">
            <div className="relative z-10">
                <div className="rounded-2xl border border-gray-300 shadow-2xl shadow-gray-300 bg-white p-8 gap-y-4 flex flex-col justify-center items-center">
                    <form className="flex flex-col gap-4 bg-white p-8 rounded-xl shadow-none" onSubmit={handleSignIn}>
                        <img className="border flex justify-center allign-center shadow w-[200px] h-[200px]" src={avatar} alt="avatar" />
                        <Input
                            ref={emailRef}
                            type="email"
                            name="email"
                            label="Email"
                            required
                            variant="primary"
                            size="md"
                            autoComplete="username"
                        />
                        <Input
                            type="password"
                            name="password"
                            label="Password"
                            required
                            variant="primary"
                            size="md"
                            autoComplete="current-password"
                        />
                        <Button type="submit" variant="primary" size="md" onClick={handleSignIn}>
                            Sign In
                        </Button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default SignIn;
