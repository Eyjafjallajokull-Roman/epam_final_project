const form = document.getElementById("mainForm");
const username = document.getElementById("userName");
const last = document.getElementById("last");
const email = document.getElementById("email");
const password = document.getElementById("pass");
const password2 = document.getElementById("pass2");

// const passwordIn = document.getElementById("passForSecondPage");
// const emailIn = document.getElementById("emailForSecondPage");

form.addEventListener("submit", (item) => {

console.log("Submit")
    checkInputs(item);
});

function checkInputs(item) {
    const usernameValue = username.value.trim();
    const emailValue = email.value.trim();
    const passwordValue = password.value.trim();
    const password2Value = password2.value.trim();
    const lastValue = last.value.trim();


    if (usernameValue === "") {
        setErrorFor(username, "Username cannot be blank",item);
    } else if (!isUsername(usernameValue)) {
        setErrorFor(username, "Not a valid username",item);
    } else {
        setSuccessFor(username);
    }


    if (lastValue === "") {
        setErrorFor(last, "Last name cannot be blank",item);
    } else if (!isLastName(lastValue)) {
        setErrorFor(last, "Not a valid last name",item);
    } else {
        setSuccessFor(last);
    }

    if (emailValue === "") {
        setErrorFor(email, "Email cannot be blank",item);
    } else if (!isEmail(emailValue)) {
        setErrorFor(email, "Not a valid email",item);
    } else {
        setSuccessFor(email);
    }



    if (passwordValue === "") {
        setErrorFor(password, "Password cannot be blank",item);
    } else if (!isPassword(passwordValue)) {
        setErrorFor(password, "Not a valid password",item);
    } else {
        setSuccessFor(password);
    }


    if (password2Value === "") {
        setErrorFor(password2, "This cell cannot be blank",item);
    } else if (!isPass2(password2Value)) {
        setErrorFor(password2, "Check your password again",item);
    } else {
        setSuccessFor(password2);
    }
}

//
function setErrorFor(input, message, item) {
    const formControl = input.parentElement;
    const p = document.getElementsByTagName("p");
    formControl.className = "form-control error";
    p.innerText = message;
    console.log("AAA")
    item.preventDefault();
}

function setSuccessFor(input) {
    const formControl = input.parentElement;
    formControl.className = "form-control success";
    console.log("SUCCESS")
}

function isEmail(email) {
    return /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(
        email
    );
}

function isPass2(password2) {
    return /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,15}$/.test(password2);
}


function isPassword(passwordIn) {
    return /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,15}$/.test(passwordIn);
}

function isUsername(username) {
    return /^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$/.test(username);
}

function isLastName(last) {
    return /^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$/.test(last);
}





