
export default function Formular(){



  const HandleSubmit = async(event) => {
    event.preventDefault();
    console.log("Submitted!");

    const fn = new FormData(event.target);
    const data = Object.fromEntries(fn.entries());
    console.log(data);

    try{
        const res = await fetch('http://172.31.252.28:8089/epital-api/add',{
          method: 'POST',
          mode:'cors',
          headers: {'Content-Type':'application/json'},
          credentials: 'same-origin',
          body : JSON.stringify(data)
        });
        const resData = await res.json();
        console.log('Response : ', resData);


    } catch (error){
      console.log('Your error is :', error);
      

    }

  };




    return(
    <>

    <form
    onSubmit={HandleSubmit}
    className="flex flex-col items-center justify-center content-normal border-2 border-gray-300 shadow-lg rounded-lg p-6 bg-gradient-to-r from-blue-50 to-indigo-50">
  <h1 className="text-3xl font-extrabold text-gray-700 mb-4">Formulary</h1>

  <div className="flex flex-col md:flex-row items-start mb-4">
    <label className="text-gray-600 md:w-1/4 mb-1 md:mb-0 md:text-right pr-2">Family Name :</label>
    <input
      type="text"
      className="border border-gray-300 text-black rounded-md shadow-sm focus:ring-2 focus:ring-indigo-400 px-3 py-2 w-full md:w-2/4"
      placeholder="ADJAZ"
      name="familyname"
      />


  </div>

  <div className="flex flex-col md:flex-row items-start mb-4">
    <label className="text-gray-600 md:w-1/4 mb-1 md:mb-0 md:text-right pr-2">Name :</label>
    <input
      type="text"
      className="border border-gray-300 text-black rounded-md shadow-sm focus:ring-2 focus:ring-indigo-400 px-3 py-2 w-full md:w-2/4"
      placeholder="Amir"
      name="name"
      />
  </div>

  <div className="flex flex-col md:flex-row items-start mb-4 ">
    <label className="text-gray-600 md:w-1/4 mb-1 md:mb-0 md:text-right pr-2">Age :</label>
    <input
      type="number"
      className="border border-gray-300  text-black rounded-md shadow-sm focus:ring-2 focus:ring-indigo-400 px-3 py-2 w-full md:w-2/4"
      placeholder="23"
      name="age"
      />
  </div>

  <div className="flex flex-col md:flex-row items-start mb-4">
    <label className="text-gray-600 md:w-1/4 mb-1 md:mb-0 md:text-right pr-2">Profession :</label>
    <input
      type="text"
      className="border border-gray-300 text-black rounded-md shadow-sm focus:ring-2 focus:ring-indigo-400 px-3 py-2 w-full md:w-2/4"
      placeholder="Doctor"
      name="profession"
      />
  </div>

  <button
    type="submit"
    className="mt-4 bg-indigo-500 text-white px-6 py-2 rounded-lg shadow hover:bg-indigo-600 transition-all duration-300 ease-in-out">
    Submit
  </button>

</form>

    </>);
};