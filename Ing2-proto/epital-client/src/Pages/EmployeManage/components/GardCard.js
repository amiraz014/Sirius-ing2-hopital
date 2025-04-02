import { useState } from 'react';
import logo from './Epitalogo.svg';

export default function GardCard({ employes }) {
  const [searchDate, setSearchDate] = useState('');
  const types = [...new Set(employes.flatMap(e => e.gardes.map(g => g.type)))];
  const secteurs = [...new Set(employes.flatMap(e => e.gardes.map(g => g.lieu.secteur)))];

  const filterGard = (secteur, type) => 
    employes.filter(e => 
      e.gardes.some(g => 
        g.lieu.secteur === secteur && 
        g.type === type &&
        (searchDate === '' || g.date.includes(searchDate))
      )
    );

  return (
    <div className="max-w-6xl mx-auto p-6 bg-white rounded-xl shadow-md">
      <div className="flex items-center space-x-4 justify-between mb-8">
        <div className="flex items-center">
          <img src={logo} alt="Logo" className="h-12 mr-4" />
          <h2 className="text-2xl font-bold text-gray-800">Planning des Gardes</h2>
        </div>
        <input
          type="date"
          placeholder="(JJ-MM-AAAA)"
          className="pl-4 pr-10 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 text-gray-800"
          value={searchDate}
          onChange={(e) => setSearchDate(e.target.value)}
        />
      </div>

      <div className="overflow-x-auto">
        <table className="w-full border-collapse">
          <thead>
            <tr className="bg-blue-50">
              <th className="p-4 text-left font-semibold text-blue-700 border-b"></th>
              {types.map(type => (
                <th key={type} className="p-4 text-left font-semibold text-blue-700 border-b">{type}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {secteurs.map(secteur => (
              <tr key={secteur} className="hover:bg-gray-50">
                <td className="p-4 font-medium text-gray-700 border-b">{secteur}</td>
                {types.map(type => {
                  const employesFiltres = filterGard(secteur, type);
                  
                  return (
                    <td key={type} className="p-4 border-b">
                      <div className="flex gap-2 overflow-x-auto">
                        {employesFiltres.map(employe => 
                          employe.gardes
                            .filter(g => 
                              g.lieu.secteur === secteur && 
                              g.type === type &&
                              (searchDate === '' || g.date.includes(searchDate))
                            )
                            .map(garde => (
                              <div key={garde.date} className="min-w-[150px] p-3 bg-blue-50 rounded-lg border border-blue-100">
                                <div className="font-medium text-blue-800">{employe.nom}</div>
                                <div className="font-medium text-blue-700">{employe.nomUtilisateur}</div>
                                <div className="text-sm text-blue-600">{type}</div>
                                <div className="text-sm text-gray-500">{garde.heure}</div>
                                <div className="text-xs text-gray-400">{garde.date}</div>
                              </div>
                            ))
                        )}
                      </div>
                    </td>
                  );
                })}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}