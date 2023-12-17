const url = "http://localhost:8080/task/user/1";

//esconde o loading:
function hideLoader(){
    document.getElementById("loading").style.display = "none";
}

function show(tasks) {
    //const tasksArray = Array.isArray(tasks) ? tasks : [tasks];

    let tab = `<thead>
              <th scope="col">#</th>
              <th scope="col">Description</th>
          </thead>`;
  
    for (let task of tasks) {
      tab += `
              <tr>
                  <td scope="row">${task.id}</td>
                  <td>${task.description}</td>
              </tr>
          `;
    }
   
    document.getElementById("tasks").innerHTML = tab;
  }

async function getAPI(url){
    const response = await fetch(url, { method: "GET" });
    var data = await response.json();

    if(response){
        hideLoader();
        
    }
    show(data);
}

getAPI(url);

