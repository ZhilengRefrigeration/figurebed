export function getselect(){
    const parmas ={
        startTime :"",
        endTime :""
    }
    return fetch("",{

    headers:{
        "Content-Type": "application/json",
    },
    method:"POST",
        body: JSON.stringify(parmas),

    }
            .then(response => response.json())

        .then(data => {
            return data.data
        })
    )
}
