# TXTcalendar
My father is visually impaired and thus can not use visual digital calendars like most of us. I therefore created this text calendar generator tool for him in Java Swing. A year is simply specified in an autofocused input field and then a text calendar for that year is created with a click of a button. Since he might not be able to click the button, I binded ENTER to that same button. By default, a text calendar for the specified year will appear on the user's desktop. This can be changed with the browse feature. The program generates a calendar in Icelandic. An example of the contents of a 2015 generated calendar:

Janúar

Fimmtudagur, 1. janúar


Föstudagur, 2. janúar


Laugardagur, 3. janúar
...

and so on until the 31st of December.

Being a text file, he could easily open it, search for the the present day and to notes in that location for later. He'd only have to open the file in his favorite text editor, do CTRL + F and navigate to a day he wanted. This approach allows him to have full accessibility to his calendar. His screenreader would would easily read each line of the text file he wishes, making his life easier.
