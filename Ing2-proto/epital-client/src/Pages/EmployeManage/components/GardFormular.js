import { useContext, useState } from "react";
import Epital from "../../Epital";
import MainMenu from "../../Home/Components/MainMenu";
import { Link } from "react-router-dom";

export default function GardFormular() {
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        try {
            const response = await fetch('http://172.31.252.28:8089/epital-api/frontData', {
                method: 'POST',
                mode: 'cors',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `startDate=${startDate}&endDate=${endDate}`
            });

            if (!response.ok) {
                throw new Error('Erreur lors de l\'envoi des données');
            }

            const result = await response.text();
            console.log('Réponse de l\'API:', result);

            setStartDate('');
            setEndDate('');

            alert('Planification envoyée avec succès !');

        } catch (err) {
            console.error('Erreur:', err);
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    const handleReset = () => {
        setStartDate('');
        setEndDate('');
        setError(null);
    };

    const con = useContext(Epital);

    return (
        <div class="flex flex-row justify-between bg-gray-900">
            <div>
                {con.showMenu && <MainMenu/>}
            </div>
        <div className="flex flex-row space-x-6 items-center justify-center min-h-screen text-white">
            <form
                className="max-w-md mx-auto px-24 py-20 bg-white shadow-md rounded-lg"
                onSubmit={handleSubmit}
            >
                <div className="flex items-center">
                        <img src={logo} alt="Logo" className="h-12 mr-4" />
                        <h2 className="text-2xl font-bold text-gray-800">Planning</h2>
                        </div>
                
                {error && (
                    <div className="mb-4 text-red-500">
                        {error}
                    </div>
                )}

                <div className="mb-4">
                    <label 
                        htmlFor="start-date" 
                        className="block text-gray-700 font-bold mb-2"
                    >
                        Date de début
                    </label>
                    <input 
                        type="date" 
                        id="start-date"
                        className="w-full px-3 py-2 border rounded-lg text-black focus:outline-none focus:ring-2 focus:ring-blue-400"
                        onChange={(e) => setStartDate(e.target.value)}
                        value={startDate}
                        required
                    />
                </div>
                
                <div className="mb-4">
                    <label 
                        htmlFor="end-date" 
                        className="block text-gray-700 font-bold mb-2"
                    >
                        Date de fin
                    </label>
                    <input 
                        type="date" 
                        id="end-date"
                        className="w-full px-3 py-2 border rounded-lg text-black focus:outline-none focus:ring-2 focus:ring-blue-400"
                        onChange={(e) => setEndDate(e.target.value)}
                        value={endDate}
                        required
                    />
                </div>
                <div class="flex flex-col space-y-4">
                <div className="flex flex-row space-x-4">
                    <div className="flex justify-between">
                        <button 
                            id="submit-button"
                            type="submit" 
                            className="bg-blue-400 text-white px-4 py-2 rounded-lg hover:bg-blue-500 transition-colors"
                            disabled={loading}
                        >
                            {loading ? 'Envoi en cours...' : 'Soumettre'}
                        </button>
                    </div>
                    <div>
                        <button 
                            type="button" 
                            onClick={handleReset}
                            className="bg-blue-400 text-white px-4 py-2 rounded-lg hover:bg-blue-500 transition-colors"
                        >
                            Réinitialiser
                        </button>
                    </div>
                </div>
                <div>
                    
                        <Link to="/EmployeView" className="border-2 border-blue-400 bg-blue-400 text-white rounded-lg h-20 w-20 p-2">Voir</Link>
                    
                </div>
                </div>
            </form>
        </div>
                <div></div>

        </div>
    );
}