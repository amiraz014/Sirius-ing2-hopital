
export default function Table(props){
  const users = props.user;
    return(
        <>
       <div className="container mx-auto mt-8 p-4 bg-white">
      <h2 className="text-3xl font-extrabold text-gray-700 mb-4">Users</h2>
      <table className="min-w-full bg-white border border-black  rounded-lg shadow-sm">
        <thead>
          <tr className="bg-gray-200">
            <th className="px-6 py-3 border-b border-black text-left text-black">Name</th>
            <th className="px-6 py-3 border-b border-black text-left text-black">Family Name</th>
            <th className="px-6 py-3 border-b border-black text-left text-black">Age</th>
            <th className="px-6 py-3 border-b border-black text-left text-black">Profession</th>
          </tr>
        </thead>
        <tbody>
        {users.map((item, index) => (
            <tr key={index} className="hover:bg-gray-100">
              <td className="px-6 py-2 border-b text-black">{item.name}</td>
              <td className="px-6 py-2 border-b text-black">{item.familyname}</td>
              <td className="px-6 py-2 border-b text-black">{item.age}</td>
              <td className="px-6 py-2 border-b text-black">{item.profession}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
        </>
    );
}