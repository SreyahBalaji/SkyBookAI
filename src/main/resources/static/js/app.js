const API_BASE = "";


// ============================================================
// TOKEN
// ============================================================

function getToken()
{
    return localStorage.getItem("skybookToken");
}


function isLoggedIn()
{
    return !!getToken();
}


// ============================================================
// REGISTER
// ============================================================

const registerForm =
    document.getElementById("registerForm");


if (registerForm)
{
    registerForm.addEventListener(
        "submit",
        async function(event)
        {
            event.preventDefault();

            const message =
                document.getElementById(
                    "registerMessage"
                );

            message.textContent =
                "Creating account...";

            try
            {
                const response =
                    await fetch(
                        "/auth/register",
                        {
                            method: "POST",

                            headers:
                                {
                                    "Content-Type":
                                        "application/json"
                                },

                            body: JSON.stringify(
                                {
                                    name:
                                        document
                                            .getElementById(
                                                "registerName"
                                            )
                                            .value
                                            .trim(),

                                    email:
                                        document
                                            .getElementById(
                                                "registerEmail"
                                            )
                                            .value
                                            .trim(),

                                    password:
                                    document
                                        .getElementById(
                                            "registerPassword"
                                        )
                                        .value,

                                    phoneNumber:
                                        document
                                            .getElementById(
                                                "registerPhone"
                                            )
                                            .value
                                            .trim(),

                                    dateOfBirth:
                                    document
                                        .getElementById(
                                            "registerDob"
                                        )
                                        .value,

                                    idProofType:
                                    document
                                        .getElementById(
                                            "registerIdType"
                                        )
                                        .value,

                                    idProofNumber:
                                        document
                                            .getElementById(
                                                "registerIdNumber"
                                            )
                                            .value
                                            .trim()
                                }
                            )
                        }
                    );

                const data =
                    await readResponse(response);

                if (!response.ok)
                {
                    throw new Error(
                        getErrorMessage(data)
                    );
                }

                message.textContent =
                    "Registration successful. Redirecting to login...";

                setTimeout(
                    function()
                    {
                        window.location.href =
                            "/login";
                    },
                    1000
                );
            }
            catch (error)
            {
                message.textContent =
                    error.message;
            }
        }
    );
}


// ============================================================
// LOGIN
// ============================================================

const loginForm =
    document.getElementById("loginForm");


if (loginForm)
{
    loginForm.addEventListener(
        "submit",
        async function(event)
        {
            event.preventDefault();

            const message =
                document.getElementById(
                    "loginMessage"
                );

            message.textContent =
                "Logging in...";

            try
            {
                const response =
                    await fetch(
                        "/auth/login",
                        {
                            method: "POST",

                            headers:
                                {
                                    "Content-Type":
                                        "application/json"
                                },

                            body: JSON.stringify(
                                {
                                    email:
                                        document
                                            .getElementById(
                                                "loginEmail"
                                            )
                                            .value
                                            .trim(),

                                    password:
                                    document
                                        .getElementById(
                                            "loginPassword"
                                        )
                                        .value
                                }
                            )
                        }
                    );

                const data =
                    await readResponse(response);

                if (!response.ok)
                {
                    throw new Error(
                        getErrorMessage(data)
                    );
                }

                localStorage.setItem(
                    "skybookToken",
                    data.token
                );

                localStorage.setItem(
                    "skybookName",
                    data.name
                );

                localStorage.setItem(
                    "skybookEmail",
                    data.email
                );

                message.textContent =
                    "Login successful.";

                window.location.href =
                    "/search-flights";
            }
            catch (error)
            {
                message.textContent =
                    error.message;
            }
        }
    );
}


// ============================================================
// LOGOUT
// ============================================================

function logout()
{
    localStorage.removeItem(
        "skybookToken"
    );

    localStorage.removeItem(
        "skybookName"
    );

    localStorage.removeItem(
        "skybookEmail"
    );

    window.location.href =
        "/login";
}


// ============================================================
// HOME SEARCH
// ============================================================

function searchFromHome()
{
    const source =
        document
            .getElementById("homeSource")
            .value
            .trim();

    const destination =
        document
            .getElementById("homeDestination")
            .value
            .trim();


    if (!source || !destination)
    {
        alert(
            "Please enter source and destination."
        );

        return;
    }


    window.location.href =
        "/search-flights?source="
        + encodeURIComponent(source)
        + "&destination="
        + encodeURIComponent(destination);
}


// ============================================================
// FLIGHT SEARCH
// ============================================================

async function searchFlights()
{
    const source =
        document
            .getElementById("flightSource")
            .value
            .trim();

    const destination =
        document
            .getElementById(
                "flightDestination"
            )
            .value
            .trim();


    const results =
        document.getElementById(
            "flightResults"
        );

    const message =
        document.getElementById(
            "flightMessage"
        );


    if (!source || !destination)
    {
        message.textContent =
            "Enter both source and destination.";

        return;
    }


    message.textContent =
        "Searching flights...";

    results.innerHTML = "";


    try
    {
        const url =
            "/flights/search?source="
            + encodeURIComponent(source)
            + "&destination="
            + encodeURIComponent(destination);


        const response =
            await fetch(url);


        const flights =
            await readResponse(response);


        if (!response.ok)
        {
            throw new Error(
                getErrorMessage(flights)
            );
        }


        message.textContent = "";


        if (!Array.isArray(flights)
            || flights.length === 0)
        {
            message.textContent =
                "No flights found for this route.";

            return;
        }


        flights.forEach(
            function(flight)
            {
                const card =
                    document.createElement(
                        "div"
                    );

                card.className =
                    "flight-card";


                card.innerHTML =
                    `
                    <div class="flight-main">

                        <div>
                            <span class="airline">
                                ${escapeHtml(flight.airline)}
                            </span>

                            <span class="flight-number">
                                ${escapeHtml(flight.flightNumber)}
                            </span>
                        </div>

                        <h3>
                            ${escapeHtml(flight.source)}
                            →
                            ${escapeHtml(flight.destination)}
                        </h3>

                        <p>
                            Departure:
                            ${formatDateTime(
                        flight.departureTime
                    )}
                        </p>

                        <p>
                            Arrival:
                            ${formatDateTime(
                        flight.arrivalTime
                    )}
                        </p>

                        <p>
                            ${flight.availableSeats}
                            seats available
                        </p>

                    </div>

                    <div class="flight-price">

                        <h2>
                            ₹${Number(
                        flight.price
                    ).toFixed(2)}
                        </h2>

                        <button
                            class="primary-button"
                            onclick="selectFlight(
                                ${flight.id}
                            )">

                            Book

                        </button>

                    </div>
                    `;


                results.appendChild(card);
            }
        );
    }
    catch (error)
    {
        message.textContent =
            error.message;
    }
}


// ============================================================
// AUTO SEARCH FROM HOMEPAGE
// ============================================================

async function initializeFlightPage()
{
    const sourceInput =
        document.getElementById(
            "flightSource"
        );

    const destinationInput =
        document.getElementById(
            "flightDestination"
        );


    if (!sourceInput
        || !destinationInput)
    {
        return;
    }


    const params =
        new URLSearchParams(
            window.location.search
        );


    const source =
        params.get("source");

    const destination =
        params.get("destination");


    if (source)
    {
        sourceInput.value =
            source;
    }


    if (destination)
    {
        destinationInput.value =
            destination;
    }


    if (source && destination)
    {
        await searchFlights();
    }
}


// ============================================================
// SELECT FLIGHT
// ============================================================

function selectFlight(flightId)
{
    if (!isLoggedIn())
    {
        localStorage.setItem(
            "pendingFlightId",
            flightId
        );

        window.location.href =
            "/login";

        return;
    }


    window.location.href =
        "/book-flight?flightId="
        + encodeURIComponent(flightId);
}


// ============================================================
// LOAD BOOKING PAGE
// ============================================================

async function loadSelectedFlight()
{
    const container =
        document.getElementById(
            "selectedFlight"
        );


    if (!container)
    {
        return;
    }


    if (!isLoggedIn())
    {
        window.location.href =
            "/login";

        return;
    }


    const params =
        new URLSearchParams(
            window.location.search
        );


    let flightId =
        params.get("flightId");


    if (!flightId)
    {
        flightId =
            localStorage.getItem(
                "pendingFlightId"
            );
    }


    if (!flightId)
    {
        container.textContent =
            "No flight selected.";

        return;
    }


    try
    {
        const response =
            await fetch(
                "/flights/" + flightId
            );


        const flight =
            await readResponse(response);


        if (!response.ok)
        {
            throw new Error(
                getErrorMessage(flight)
            );
        }


        localStorage.setItem(
            "selectedFlightId",
            flight.id
        );


        container.innerHTML =
            `
            <div class="flight-card">

                <div>

                    <h2>
                        ${escapeHtml(flight.airline)}
                        ${escapeHtml(flight.flightNumber)}
                    </h2>

                    <h3>
                        ${escapeHtml(flight.source)}
                        →
                        ${escapeHtml(flight.destination)}
                    </h3>

                    <p>
                        Departure:
                        ${formatDateTime(
                flight.departureTime
            )}
                    </p>

                    <p>
                        Arrival:
                        ${formatDateTime(
                flight.arrivalTime
            )}
                    </p>

                </div>

                <div>

                    <h2>
                        ₹${Number(
                flight.price
            ).toFixed(2)}
                    </h2>

                    <p>
                        ${flight.availableSeats}
                        seats available
                    </p>

                </div>

            </div>
            `;
    }
    catch (error)
    {
        container.textContent =
            error.message;
    }
}


// ============================================================
// CREATE BOOKING
// ============================================================

async function confirmBooking()
{
    const message =
        document.getElementById(
            "bookingMessage"
        );


    if (!isLoggedIn())
    {
        window.location.href =
            "/login";

        return;
    }


    const flightId =
        localStorage.getItem(
            "selectedFlightId"
        );


    const seat =
        document
            .getElementById(
                "seatNumber"
            )
            .value
            .trim()
            .toUpperCase();


    if (!flightId)
    {
        message.textContent =
            "No flight selected.";

        return;
    }


    if (!seat)
    {
        message.textContent =
            "Enter a seat number.";

        return;
    }


    message.textContent =
        "Creating booking...";


    try
    {
        const response =
            await fetch(
                "/bookings",
                {
                    method: "POST",

                    headers:
                        {
                            "Content-Type":
                                "application/json",

                            "Authorization":
                                "Bearer "
                                + getToken()
                        },

                    body: JSON.stringify(
                        {
                            flightId:
                                Number(flightId),

                            seatNumber:
                            seat
                        }
                    )
                }
            );


        const booking =
            await readResponse(response);


        if (response.status === 401
            || response.status === 403)
        {
            logout();
            return;
        }


        if (!response.ok)
        {
            throw new Error(
                getErrorMessage(booking)
            );
        }


        localStorage.removeItem(
            "pendingFlightId"
        );


        localStorage.removeItem(
            "selectedFlightId"
        );


        message.innerHTML =
            `
            Booking confirmed!<br>
            Reference:
            <strong>
                ${escapeHtml(
                booking.bookingReference
            )}
            </strong>
            <br>
            Confirmation email has been sent.
            `;


        setTimeout(
            function()
            {
                window.location.href =
                    "/my-trips";
            },
            1800
        );
    }
    catch (error)
    {
        message.textContent =
            error.message;
    }
}


// ============================================================
// MY TRIPS
// ============================================================

async function loadMyTrips()
{
    const container =
        document.getElementById(
            "tripResults"
        );


    const message =
        document.getElementById(
            "tripMessage"
        );


    if (!container)
    {
        return;
    }


    if (!isLoggedIn())
    {
        window.location.href =
            "/login";

        return;
    }


    message.textContent =
        "Loading your trips...";


    try
    {
        const response =
            await fetch(
                "/bookings/my",
                {
                    headers:
                        {
                            "Authorization":
                                "Bearer "
                                + getToken()
                        }
                }
            );


        const bookings =
            await readResponse(response);


        if (response.status === 401
            || response.status === 403)
        {
            logout();
            return;
        }


        if (!response.ok)
        {
            throw new Error(
                getErrorMessage(bookings)
            );
        }


        message.textContent = "";
        container.innerHTML = "";


        if (!Array.isArray(bookings)
            || bookings.length === 0)
        {
            message.textContent =
                "You don't have any bookings yet.";

            return;
        }


        bookings.forEach(
            function(booking)
            {
                const card =
                    document.createElement(
                        "div"
                    );


                card.className =
                    "trip-card";


                const cancelled =
                    booking.bookingStatus
                    === "CANCELLED";


                card.innerHTML =
                    `
                    <div>

                        <span class="status-badge
                            ${cancelled
                        ? "cancelled"
                        : "confirmed"}">

                            ${escapeHtml(
                        booking.bookingStatus
                    )}

                        </span>

                        <h2>
                            ${escapeHtml(
                        booking.airline
                    )}
                            ${escapeHtml(
                        booking.flightNumber
                    )}
                        </h2>

                        <h3>
                            ${escapeHtml(
                        booking.source
                    )}
                            →
                            ${escapeHtml(
                        booking.destination
                    )}
                        </h3>

                        <p>
                            Booking Reference:
                            <strong>
                                ${escapeHtml(
                        booking.bookingReference
                    )}
                            </strong>
                        </p>

                        <p>
                            Seat:
                            ${escapeHtml(
                        booking.seatNumber
                    )}
                        </p>

                        <p>
                            Departure:
                            ${formatDateTime(
                        booking.departureTime
                    )}
                        </p>

                        <p>
                            ₹${Number(
                        booking.price
                    ).toFixed(2)}
                        </p>

                    </div>

                    <div>

                        ${
                        cancelled
                            ?
                            ""
                            :
                            `
                            <button
                                class="danger-button"
                                onclick="cancelBooking(
                                    ${booking.id}
                                )">

                                Cancel Booking

                            </button>
                            `
                    }

                    </div>
                    `;


                container.appendChild(card);
            }
        );
    }
    catch (error)
    {
        message.textContent =
            error.message;
    }
}


// ============================================================
// CANCEL BOOKING
// ============================================================

async function cancelBooking(bookingId)
{
    const confirmed =
        confirm(
            "Are you sure you want to cancel this booking?"
        );


    if (!confirmed)
    {
        return;
    }


    try
    {
        const response =
            await fetch(
                "/bookings/"
                + bookingId
                + "/cancel",
                {
                    method: "PUT",

                    headers:
                        {
                            "Authorization":
                                "Bearer "
                                + getToken()
                        }
                }
            );


        const data =
            await readResponse(response);


        if (response.status === 401
            || response.status === 403)
        {
            logout();
            return;
        }


        if (!response.ok)
        {
            throw new Error(
                getErrorMessage(data)
            );
        }


        alert(
            "Booking cancelled successfully."
        );


        await loadMyTrips();
    }
    catch (error)
    {
        alert(error.message);
    }
}


// ============================================================
// AI CHAT WIDGET
// ============================================================

function createAIWidget()
{
    if (document.getElementById(
        "aiChatButton"
    ))
    {
        return;
    }


    const wrapper =
        document.createElement("div");


    wrapper.innerHTML =
        `
        <button
            id="aiChatButton"
            class="ai-chat-button"
            onclick="toggleAIChat()">

            AI

        </button>


        <div
            id="aiChatWindow"
            class="ai-chat-window">

            <div class="ai-header">

                <div>
                    <strong>
                        SkyBookAI Assistant
                    </strong>

                    <small>
                        Flight & booking help
                    </small>
                </div>

                <button
                    onclick="toggleAIChat()">
                    ×
                </button>

            </div>


            <div
                id="aiMessages"
                class="ai-messages">

                <div class="ai-message bot">
                    Hi! How can I help with
                    your journey?
                </div>

            </div>


            <div class="ai-input-area">

                <input
                    id="aiInput"
                    type="text"
                    placeholder="Ask SkyBookAI...">

                <button
                    onclick="sendAIMessage()">

                    Send

                </button>

            </div>

        </div>
        `;


    document.body.appendChild(
        wrapper
    );


    const input =
        document.getElementById(
            "aiInput"
        );


    input.addEventListener(
        "keydown",
        function(event)
        {
            if (event.key === "Enter")
            {
                sendAIMessage();
            }
        }
    );
}


function toggleAIChat()
{
    const chat =
        document.getElementById(
            "aiChatWindow"
        );


    chat.classList.toggle(
        "open"
    );
}


async function sendAIMessage()
{
    const input =
        document.getElementById(
            "aiInput"
        );


    const messages =
        document.getElementById(
            "aiMessages"
        );


    const text =
        input.value.trim();


    if (!text)
    {
        return;
    }


    addAIMessage(
        text,
        "user"
    );


    input.value = "";


    try
    {
        const response =
            await fetch(
                "/ai/chat",
                {
                    method: "POST",

                    headers:
                        {
                            "Content-Type":
                                "application/json"
                        },

                    body: JSON.stringify(
                        {
                            message: text
                        }
                    )
                }
            );


        const data =
            await readResponse(response);


        if (!response.ok)
        {
            throw new Error(
                getErrorMessage(data)
            );
        }


        addAIMessage(
            data.response,
            "bot"
        );
    }
    catch (error)
    {
        addAIMessage(
            "Sorry, I couldn't process that request.",
            "bot"
        );
    }


    messages.scrollTop =
        messages.scrollHeight;
}


function addAIMessage(text, type)
{
    const messages =
        document.getElementById(
            "aiMessages"
        );


    const bubble =
        document.createElement("div");


    bubble.className =
        "ai-message " + type;


    bubble.textContent =
        text;


    messages.appendChild(
        bubble
    );


    messages.scrollTop =
        messages.scrollHeight;
}


// ============================================================
// RESPONSE HELPERS
// ============================================================

async function readResponse(response)
{
    const text =
        await response.text();


    if (!text)
    {
        return {};
    }


    try
    {
        return JSON.parse(text);
    }
    catch
    {
        return {
            message: text
        };
    }
}


function getErrorMessage(data)
{
    if (!data)
    {
        return "Something went wrong.";
    }


    if (typeof data === "string")
    {
        return data;
    }


    if (data.message)
    {
        return data.message;
    }


    if (data.error)
    {
        return data.error;
    }


    const values =
        Object.values(data);


    if (values.length > 0)
    {
        return values.join(", ");
    }


    return "Something went wrong.";
}


function formatDateTime(value)
{
    if (!value)
    {
        return "Not available";
    }


    const date =
        new Date(value);


    if (Number.isNaN(
        date.getTime()
    ))
    {
        return value;
    }


    return date.toLocaleString();
}


function escapeHtml(value)
{
    if (value === null
        || value === undefined)
    {
        return "";
    }


    const div =
        document.createElement("div");


    div.textContent =
        String(value);


    return div.innerHTML;
}


// ============================================================
// PAGE INITIALIZATION
// ============================================================

document.addEventListener(
    "DOMContentLoaded",
    async function()
    {
        createAIWidget();

        await initializeFlightPage();

        await loadSelectedFlight();

        await loadMyTrips();


        const logoutButton =
            document.getElementById(
                "logoutButton"
            );


        const loginNav =
            document.getElementById(
                "loginNav"
            );


        if (logoutButton)
        {
            logoutButton.style.display =
                isLoggedIn()
                    ? "inline-block"
                    : "none";
        }


        if (loginNav && isLoggedIn())
        {
            loginNav.style.display =
                "none";
        }
    }
);