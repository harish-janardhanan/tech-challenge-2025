import {useState} from 'react'
import AppRouter from "./AppRouter";

function App() {
    const [count, setCount] = useState(0)

    return (
        <div
            id="root"
            className="min-h-screen w-full font-sans text-slate-900 bg-slate-50 bg-[radial-gradient(circle_at_20%_20%,rgba(203,213,225,0.7)_0,transparent_70%),radial-gradient(circle_at_80%_80%,rgba(99,102,241,0.08)_0,transparent_70%)]"
        >
            <AppRouter/>
        </div>
    )
}

export default App
