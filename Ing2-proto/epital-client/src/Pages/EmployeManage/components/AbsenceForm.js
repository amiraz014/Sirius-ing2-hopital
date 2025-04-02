import { useState, useContext } from "react";
import Epital from "../../Epital";
import { FaUserAlt } from "react-icons/fa";

export default function AbsenceForm() {
    const { username } = useContext(Epital);
    const [form, setForm] = useState({
        AbsenceDate: '',  
        AbsenceTime: '',
        AbsenceReason: '' 
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        
        if (!form.AbsenceDate || !form.AbsenceTime || !form.AbsenceReason) {
            setError('Tous les champs sont obligatoires');
            return;
        }

        setLoading(true);

        try {
           
            const formData = new URLSearchParams();
            formData.append('AbsenceDate', form.AbsenceDate);
            formData.append('AbsenceTime', form.AbsenceTime);
            formData.append('AbsenceReason', form.AbsenceReason);
            formData.append('username', username);

            const response = await fetch('http://172.31.249.204:8089/epital-api/addAbsence', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                },
                body: formData
            });

            if (!response.ok) {
                const errorData = await response.text();
                throw new Error(errorData || 'Erreur serveur');
            }

           
            setForm({ 
                AbsenceDate: '', 
                AbsenceTime: '', 
                AbsenceReason: '' 
            });
            alert('Absence enregistrée avec succès !');
            
        } catch (err) {
            console.error('Erreur détaillée:', err);
            setError(err.message || 'Erreur lors de l\'envoi du formulaire');
        } finally {
            setLoading(false);
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm(prev => ({ ...prev, [name]: value }));
    };

    return (
        <div className="max-w-md mx-auto p-6 bg-white rounded-lg shadow-md">
            <h1 className="text-xl font-bold mb-4 flex items-center gap-2">
                <FaUserAlt className="text-blue-500" /> 
                <span>Déclaration d'absence - {username}</span>
            </h1>

            {error && (
                <div className="mb-4 p-2 bg-red-100 text-red-700 rounded">
                    {error}
                </div>
            )}

            <form onSubmit={handleSubmit} className="space-y-4">
                <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">
                        Date d'absence
                    </label>
                    <input
                        type="date"
                        name="AbsenceDate"
                        value={form.AbsenceDate}
                        onChange={handleChange}
                        className="w-full p-2 border border-gray-300 rounded-md focus:ring-blue-500 focus:border-blue-500"
                        required
                    />
                </div>

                <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">
                        Heure d'absence
                    </label>
                    <input
                        type="time"
                        name="AbsenceTime"
                        value={form.AbsenceTime}
                        onChange={handleChange}
                        className="w-full p-2 border border-gray-300 rounded-md"
                        required
                    />
                </div>

                <div>
                    <label className="block text-sm font-medium text-gray-700 mb-1">
                        Motif d'absence
                    </label>
                    <textarea
                        name="AbsenceReason"
                        value={form.AbsenceReason}
                        onChange={handleChange}
                        rows={3}
                        className="w-full p-2 border border-gray-300 rounded-md"
                        required
                    />
                </div>

                <button
                    type="submit"
                    disabled={loading}
                    className={`w-full py-2 px-4 rounded-md text-white font-medium ${
                        loading 
                            ? 'bg-gray-400 cursor-not-allowed' 
                            : 'bg-blue-600 hover:bg-blue-700'
                    }`}
                >
                    {loading ? 'Envoi en cours...' : 'Envoyer la déclaration'}
                </button>
            </form>
        </div>
    );
}