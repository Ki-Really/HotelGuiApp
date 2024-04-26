# HotelApp
Project for admins of hotel. Made using Java, PostgreSQL, Hibernate, JavaFX. There is an opportunity to add person, maid, schedule etc. to database. Opportunity to update and delete entities. Created JavaFX GUI 
for making admins work easier. Also there is an opportunity to search by fields, which are the names of columns. 
Here I use MVC pattern, where: 
- Models are my entities, where I describe their parameters (database fields).
- Views are my .fxml files which are responsible for GUI.
- Controllers are classes where I switch scenes and making managing stuff.

## Gui looks like
![Снимок-экрана-2024-04-24-170028](https://github.com/Ki-Really/HotelGuiApp/assets/133647432/95e0fb42-ec1d-4099-93a0-98f8b2e1de2d)
### Change data
![image](https://github.com/Ki-Really/HotelGuiApp/assets/133647432/edbb2289-78b2-4953-ba5f-8176db553e28)
### Search working
![image](https://github.com/Ki-Really/HotelGuiApp/assets/133647432/d840676f-f46d-4fe4-843e-35e8df31db1d)

## Database scheme looks like
![image](https://github.com/Ki-Really/HotelGuiApp/assets/133647432/29b2273c-44a4-44d4-b1e3-45f593a8ae7f)

- Table guest– guest data (passport, address, etc.). 
- Table address – address data.
- Table passport – passport data.
- Table room – room data.
- Table maid – maid data (address, name, surname, patronymic).
- Table schedule – schedule of cleaning (for each maid).
- Table service – additional service for guests (spa, swimming pool, PS4 etc.).

