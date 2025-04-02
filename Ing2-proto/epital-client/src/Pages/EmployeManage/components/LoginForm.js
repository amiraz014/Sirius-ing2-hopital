import { useContext, useState } from 'react';
import Epital from '../../Epital';
import MainMenu from "./Home/Components/mainMenu";
import { useNavigate } from 'react-router-dom';

export default function LoginForm() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const navigate = useNavigate();
    const con = useContext(Epital);
    con.setUsername(username);

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        
        if (!username.trim() || !password.trim()) {
            setError('Veuillez remplir tous les champs');
            return;
        }

        setLoading(true);
        setError(null);

        try {
            const response = await fetch('http://172.31.249.204:8089/epital-api/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    username: username.trim(),
                    password: password.trim()
                }),
                credentials: 'include' 
            });

            const data = await response.json();
            
            if (!response.ok) {
                
                const errorMsg = data.error || 
                               (response.status === 401 ? 'Identifiants incorrects' : 
                                `Erreur serveur (${response.status})`);
                throw new Error(errorMsg);
            }

            if (!data.token) {
                throw new Error('Token non reçu du serveur');
            }

            
            localStorage.setItem('authToken', data.token);
            if (con.setAuthToken) {
                con.setAuthToken(data.token);
            }

           
            navigate('/absence', { replace: true });

        } catch (err) {
            console.error('Erreur d\'authentification:', err);
            
           
            let userFriendlyError = err.message;
            if (err.message.includes('Failed to fetch')) {
                userFriendlyError = 'Impossible de se connecter au serveur';
            } else if (err.message.includes('NetworkError')) {
                userFriendlyError = 'Problème de réseau';
            }
            
            setError(userFriendlyError);
        } finally {
            setLoading(false);
        }
    }

    const handleReset = () => {
        setUsername('');
        setPassword('');
        setError(null);
    }

    return(
        <>
            <div className="flex flex-row justify-between min-h-screen bg-gray-800">
                <div className='flex items-start justify-start'>
                    {con.showMenu && <MainMenu/>}
                </div>
            
                <div className="flex flex-col items-center justify-center h-screen">
                    <h1 className="text-white text-2xl font-bold uppercase mb-6">
                        Connexion à Epital
                    </h1>
                    <form 
                        className="flex flex-col space-y-4 items-center justify-center border p-6 bg-white shadow-lg rounded-lg w-96"
                        onSubmit={handleSubmit}
                    >
                        {error && (
                            <div className="mb-4 p-3 text-red-600 bg-red-50 rounded text-sm w-full text-center border border-red-100">
                                {error}
                            </div>
                        )}

                        <div className="flex flex-col w-full space-y-2">
                            <label className="text-gray-700 text-sm font-semibold">Nom d'utilisateur</label>
                            <input 
                                type="text" 
                                className="border border-gray-300 rounded p-2 w-full focus:ring-2 focus:ring-blue-200 focus:border-blue-500" 
                                onChange={(e) => setUsername(e.target.value)}
                                value={username}
                                required
                                disabled={loading}
                                autoComplete="username"
                                placeholder="Entrez votre nom d'utilisateur"
                            />
                        </div>

                        <div className="flex flex-col w-full space-y-2">
                            <label className="text-gray-700 text-sm font-semibold">Mot de passe</label>
                            <input 
                                type="password" 
                                className="border border-gray-300 rounded p-2 w-full focus:ring-2 focus:ring-blue-200 focus:border-blue-500" 
                                onChange={(e) => setPassword(e.target.value)}
                                value={password}
                                required
                                disabled={loading}
                                autoComplete="current-password"
                                placeholder="Entrez votre mot de passe"
                            />
                        </div>

                        <div className="flex flex-row space-x-4 pt-4 w-full">
                            <button 
                                type="submit" 
                                className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 transition flex-1 disabled:bg-blue-300 disabled:cursor-not-allowed flex items-center justify-center"
                                disabled={loading}
                            >
                                {loading ? (
                                    <>
                                        <svg className="animate-spin -ml-1 mr-2 h-4 w-4 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                            <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                                            <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                                        </svg>
                                        Connexion...
                                    </>
                                ) : 'Se connecter'}
                            </button>
                            <button
                                type="button"
                                onClick={handleReset}
                                className="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600 transition flex-1 disabled:bg-gray-300 disabled:cursor-not-allowed"
                                disabled={loading}
                            >
                                Réinitialiser
                            </button>
                        </div>

                        <div className="pt-2 text-sm text-gray-600">
                            <a href="/forgot-password" className="text-blue-600 hover:underline">Mot de passe oublié ?</a>
                        </div>
                    </form>
                </div>
                <div></div>
            </div>
        </>
    );
}