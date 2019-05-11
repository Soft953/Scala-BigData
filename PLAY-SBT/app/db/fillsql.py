import sqlite3

connection = sqlite3.connect("mydb.db")

cursor = connection.cursor()

sql_command = """
CREATE TABLE history (
car_id INT NOT NULL,
location VARCHAR(50),
speed FLOAT,
acceleration FLOAT,
fuel FLOAT,
engineTemp FLOAT,
nextStep VARCHAR(50));"""

cursor.execute(sql_command)

for i in range(1,11):
    for j in range(1, 21):
        f = float(j)
        sql_command = """INSERT INTO history VALUES (""" + str(i) + """, "WeedMill", """ + str(f) + """, 2.5, 34.5, 80, "house");"""        
        cursor.execute(sql_command)

connection.commit()
connection.close()
