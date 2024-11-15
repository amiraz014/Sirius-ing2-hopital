
export default function Table(){

    return(
        <>
       <div className="container mx-auto mt-8 p-4 bg-white">
      <h2 className="text-3xl font-extrabold text-gray-700 mb-4">Users</h2>
      <table className="min-w-full bg-white border border-black  rounded-lg shadow-sm">
        <thead>
          <tr className="bg-gray-200">
            <th className="px-6 py-3 border-b border-black text-left text-black">Name</th>
            <th className="px-6 py-3 border-b border-black text-left text-black">First Name</th>
            <th className="px-6 py-3 border-b border-black text-left text-black">Age</th>
            <th className="px-6 py-3 border-b border-black text-left text-black">Profession</th>
          </tr>
        </thead>
        <tbody>
          
        </tbody>
      </table>
    </div>
        </>
    );
}