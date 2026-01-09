import { useRef, useState, useEffect } from "react";
import HttpClient from "../lib/HttpClient.tsx";



const Login = () => {
    const userRef = useRef<any>();

    const [user, setUser] = useState('');


    const [pwd, setPwd] = useState('');


    useEffect(() => {
        userRef.current.focus();
    }, [])


    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();


        try {
            await HttpClient.post("/auth/login", {
            login:user,
            password:pwd,
            }).then(res => {
                console.log(res.data.token);
                // localStorage.setItem("token", res.data.token);
                 const token = res.data.token;

                // Store JWT in a cookie
                document.cookie = `token=${token}; path=/; Secure; SameSite=Strict`;
            });

        } catch (error) {
            console.error(error);
            alert("Login failed");
        }
  }

    return (
        <section>
            <h1>Login</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">
                    Username:
                </label>
                <input
                    type="text"
                    id="username"
                    ref={userRef}
                    autoComplete="off"
                    onChange={(e) => setUser(e.target.value)}
                    required
                    aria-describedby="uidnote"
                />

                <label htmlFor="password">
                    Password:
                </label>
                <input
                    type="password"
                    id="password"
                    onChange={(e) => setPwd(e.target.value)}
                    required
                    aria-describedby="pwdnote"
                />
               

                <button 
                >Sign in</button>
            </form>
        </section>
    )
}

export default Login