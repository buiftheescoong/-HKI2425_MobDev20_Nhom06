//package com.example.soundnova
//
//val sampleSongList = listOf(
//    SongData(
//        name = "Viva la Vida",
//        artists = listOf("Shalom Margaret"),
//        imageUrl = "https://i.scdn.co/image/ab67616d00001e0209426d9ae9d8d981735ebc5e",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Full%20Lyrics%20%20Vietsub%20Viva%20la%20Vida%20%20Shalom%20Margaret%20cover.mp3?alt=media&token=bbd27031-2f2f-438f-8986-d93a7a640b74",
//        duration = 168000
//    ),
//    SongData(
//        name = "Viva la Vida",
//        artists = listOf("Coldplay"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/viva%20la%20vid.jpg?alt=media&token=2f8f6caf-8682-4ae6-9760-a44cc8f322e8",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Coldplay%20%20Viva%20La%20Vida%20Official%20Video.mp3?alt=media&token=411ba064-e2b5-4677-a37f-141d09a0106f",
//        duration = (4 * 60 + 2) * 1000,
//        lyrics = "I used to rule the world\n" +
//                "Seas would rise when I gave the word\n" +
//                "Now in the morning, I sleep alone\n" +
//                "Sweep the streets I used to own\n" +
//                "I used to roll the dice\n" +
//                "Feel the fear in my enemy's eyes\n" +
//                "Listen as the crowd would sing\n" +
//                "Now the old king is dead, long live the king\n" +
//                "One minute, I held the key\n" +
//                "Next the walls were closed on me\n" +
//                "And I discovered that my castles stand\n" +
//                "Upon pillars of salt and pillars of sand\n" +
//                "I hear Jerusalem bells a-ringin'\n" +
//                "Roman Cavalry choirs are singin'\n" +
//                "Be my mirror, my sword and shield\n" +
//                "My missionaries in a foreign field\n" +
//                "For some reason, I can't explain\n" +
//                "Once you'd gone, there was never, never an honest word\n" +
//                "And that was when I ruled the world\n" +
//                "It was a wicked and wild wind\n" +
//                "Blew down the doors to let me in\n" +
//                "Shattered windows and the sound of drums\n" +
//                "People couldn't believe what I'd become\n" +
//                "Revolutionaries wait\n" +
//                "For my head on a silver plate\n" +
//                "Just a puppet on a lonely string\n" +
//                "Oh, who would ever want to be king?\n" +
//                "I hear Jerusalem bells a-ringin'\n" +
//                "Roman Cavalry choirs are singing\n" +
//                "Be my mirror, my sword and shield\n" +
//                "My missionaries in a foreign field\n" +
//                "For some reason, I can't explain\n" +
//                "I know Saint Peter won't call my name\n" +
//                "Never an honest word\n" +
//                "But that was when I ruled the world\n" +
//                "Oh-oh-oh, oh-oh, oh\n" +
//                "Oh-oh-oh, oh-oh, oh\n" +
//                "Oh-oh-oh, oh-oh, oh\n" +
//                "Oh-oh-oh, oh-oh, oh\n" +
//                "Oh-oh-oh, oh-oh, oh\n" +
//                "I hear Jerusalem bells a-ringin'\n" +
//                "Roman Cavalry choirs are singin'\n" +
//                "Be my mirror, my sword and shield\n" +
//                "My missionaries in a foreign field\n" +
//                "For some reason I can't explain\n" +
//                "I know Saint Peter won't call my name\n" +
//                "Never an honest word\n" +
//                "But that was when I ruled the world",
//        genre = "Pop"
//    ),
//    SongData(
//        name = "Bille Jean",
//        artists = listOf("Michael Jackson"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/billie%20jean.jpg?alt=media&token=c537eea0-6f7f-4b75-a627-be188ce8e639",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Billie%20Jean.mp3?alt=media&token=11b23c3a-802e-4679-a098-22403fab3049",
//        duration = (4 * 60 + 54) * 1000,
//        lyrics = "She was more like a beauty queen from a movie scene\n" +
//                "I said don't mind, but what do you mean, I am the one\n" +
//                "Who will dance on the floor in the round?\n" +
//                "She said I am the one, who will dance on the floor in the round\n" +
//                "She told me her name was Billie Jean, as she caused a scene\n" +
//                "Then every head turned with eyes that dreamed of being the one\n" +
//                "Who will dance on the floor in the round\n" +
//                "People always told me be careful of what you do\n" +
//                "And don't go around breaking young girls' hearts\n" +
//                "And mother always told me be careful of who you love\n" +
//                "And be careful of what you do 'cause the lie becomes the truth\n" +
//                "Billie Jean is not my lover\n" +
//                "She's just a girl who claims that I am the one\n" +
//                "But the kid is not my son\n" +
//                "She says I am the one, but the kid is not my son\n" +
//                "For forty days and forty nights\n" +
//                "The law was on her side\n" +
//                "But who can stand when she's in demand\n" +
//                "Her schemes and plans\n" +
//                "'Cause we danced on the floor in the round\n" +
//                "So take my strong advice, just remember to always think twice\n" +
//                "(Do think twice, do think twice)\n" +
//                "She told my baby we'd danced 'til three, then she looked at me\n" +
//                "Then showed a photo my baby cried his eyes were like mine (oh, no)\n" +
//                "'Cause we danced on the floor in the round, baby\n" +
//                "People always told me be careful of what you do\n" +
//                "And don't go around breaking young girls' hearts\n" +
//                "She came and stood right by me\n" +
//                "Just the smell of sweet perfume\n" +
//                "This happened much too soon\n" +
//                "She called me to her room\n" +
//                "Billie Jean is not my lover\n" +
//                "She's just a girl who claims that I am the one\n" +
//                "But the kid is not my son\n" +
//                "Billie Jean is not my lover\n" +
//                "She's just a girl who claims that I am the one\n" +
//                "But the kid is not my son\n" +
//                "She says I am the one, but the kid is not my son\n" +
//                "She says I am the one, but the kid is not my son\n" +
//                "Billie Jean is not my lover\n" +
//                "She's just a girl who claims that I am the one\n" +
//                "But the kid is not my son\n" +
//                "She says I am the one, but the kid is not my son\n" +
//                "She says I am the one\n" +
//                "You know what you did, (she says he is my son) breaking my heart babe\n" +
//                "She says I am the one\n" +
//                "Billie Jean is not my lover\n" +
//                "Billie Jean is not my lover\n" +
//                "Billie Jean is not my lover\n" +
//                "Billie Jean is not my lover (don't Billie Jean)\n" +
//                "Billie Jean is not my lover\n" +
//                "Billie Jean is not my lover",
//        genre = "Pop"
//    ),
//    SongData(
//        name = "Smooth Criminal",
//        artists = listOf("Michael Jackson"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/smooth%20criminal.jpg?alt=media&token=bde5544f-7f01-446a-9f9c-ae8f271b4130",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Michael%20Jackson%20%20Smooth%20Criminal%20Official%20Video%20%20Shortened%20Version.mp3?alt=media&token=f54f4582-ee12-4996-814e-a031982d65f6",
//        duration = (4 * 60 + 10) * 1000,
//        lyrics = "As he came into the window\n" +
//                "It was the sound of a crescendo\n" +
//                "He came into her apartment\n" +
//                "He left the bloodstains on the carpet\n" +
//                "She ran underneath the table\n" +
//                "He could see she was unable\n" +
//                "So she ran into the bedroom\n" +
//                "She was struck down\n" +
//                "It was her doom\n" +
//                "Annie, are you okay?\n" +
//                "So, Annie, are you okay?\n" +
//                "Are you okay, Annie?\n" +
//                "Annie, are you okay?\n" +
//                "So, Annie, are you okay?\n" +
//                "Are you okay, Annie?\n" +
//                "Annie, are you okay?\n" +
//                "So, Annie, are you okay?\n" +
//                "Are you okay, Annie?\n" +
//                "Annie, are you okay?\n" +
//                "So, Annie, are you okay?\n" +
//                "Are you okay, Annie?\n" +
//                "Annie, are you okay?\n" +
//                "Will you tell us that you're okay?\n" +
//                "There's a sign in the window\n" +
//                "That he struck you - a crescendo, Annie\n" +
//                "He came into your apartment\n" +
//                "He left the bloodstains on the carpet\n" +
//                "Then you ran into the bedroom\n" +
//                "You were struck down\n" +
//                "It was your doom\n" +
//                "Annie, are you okay?\n" +
//                "So, Annie, are you okay?\n" +
//                "Are you okay, Annie?\n" +
//                "Annie, are you okay?\n" +
//                "So, Annie, are you okay?\n" +
//                "Are you okay, Annie?\n" +
//                "You've been hit by\n" +
//                "You've been hit by a smooth criminal\n" +
//                "Ow!\n" +
//                "So they came into the outway\n" +
//                "It was sunday what a black day\n" +
//                "Mouth to mouth resus citation\n" +
//                "Sounding heartbeats intimidations\n" +
//                "Annie, are you okay? (Annie, are you okay?)\n" +
//                "Annie, are you okay?\n" +
//                "Are you okay, Annie?\n" +
//                "Annie, are you okay? (Annie, are you okay?)\n" +
//                "Annie, are you okay?\n" +
//                "So, Annie, are you okay?\n" +
//                "Are you okay, Annie?\n" +
//                "Annie, are you okay?\n" +
//                "So, Annie, are you okay?\n" +
//                "Are you okay, Annie?\n" +
//                "Annie, are you okay?\n" +
//                "Will you tell us that you're okay?\n" +
//                "There's a sign in the window\n" +
//                "That he struck you - a crescendo, Annie\n" +
//                "He came into your apartment\n" +
//                "He left the bloodstains on the carpet\n" +
//                "Then you ran into the bedroom\n" +
//                "You were struck down\n" +
//                "It was your doom\n" +
//                "Annie, are you okay?\n" +
//                "So, Annie, are you okay?\n" +
//                "Are you okay, Annie?\n" +
//                "You've been hit by\n" +
//                "You've been struck by a smooth criminal\n" +
//                "Ow!\n" +
//                "Okay, I Want Everybody To Clear The Area Right Now!\n" +
//                "Annie, are you okay? (I don't know)\n" +
//                "Will you tell us that you're okay? (I don't know)\n" +
//                "There's a sound at the window (I don't know)\n" +
//                "Then he struck you, a crescendo, Annie? (I don't know)\n" +
//                "He came into your apartment (I don't know)\n" +
//                "Left the bloodstains on the carpet (I don't know why, baby)\n" +
//                "And then you ran into the bedroom (Help me!)\n" +
//                "You were struck down, it was your doom, Annie (Dag gone it!)\n" +
//                "Annie, are you okay? (Dag gone it, baby!)\n" +
//                "Will you tell us that you're okay? (Dag gone it, baby!)\n" +
//                "There's a sound at the window (Dag gone it, baby!)\n" +
//                "Then he struck you, a crescendo, Annie?\n" +
//                "He came into your apartment (Dag gone it!)\n" +
//                "Left the bloodstains on the carpet\n" +
//                "And then you ran into the bedroom\n" +
//                "You were struck down, it was your doom, Annie (Dag gone it!)",
//        genre = "Pop"
//    ),
//    SongData(
//        name = "Tìm Lại",
//        artists = listOf("Microwave"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/t%C3%ACm%20l%E1%BA%A1i.jpg?alt=media&token=ad2196a5-562f-4e12-92d5-be71a1f686a5",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20MicroWave%20%20%20Tim%20Lai.mp3?alt=media&token=0cf9f96f-de90-42fc-89df-d403f4794b32",
//        duration = (3 * 60 + 45) * 1000,
//        lyrics = "Chẳng muốn nghe gì, chẳng muốn tìm khác\n" +
//                "Đôi mắt luôn tìm, tìm về\n" +
//                "Người nhếch môi cười, người như hụt hẫng\n" +
//                "Người khóc trong lòng, hết rồi\n" +
//                "Tìm lại đi hãy tìm lại trong mỗi người\n" +
//                "Để ta không thấy ta như lúc này\n" +
//                "Đường xa quá dài rồi ta mệt nhoài\n" +
//                "Vì ta không đứng bên nhau nữa rồi\n" +
//                "Ngồi đếm sao tàn, ngày ơi đừng đến\n" +
//                "Đừng đến vội vàng, hết rồi\n" +
//                "Ngồi xuống đây bạn, cạn chén rượu kia\n" +
//                "Cạn hết đêm này, hết rồi\n" +
//                "Tìm lại đi hãy tìm lại trong mỗi người\n" +
//                "Để ta không thấy ta như lúc này\n" +
//                "Đường xa quá dài rồi ta mệt nhoài\n" +
//                "Vì ta không đứng bên nhau nữa rồi\n" +
//                "Tìm lại đi hãy tìm lại trong mỗi người\n" +
//                "Để ta không thấy ta như lúc này\n" +
//                "Đường xa quá dài rồi ta mệt nhoài\n" +
//                "Vì ta không đứng bên nhau nữa...\n" +
//                "Tìm lại đi hãy tìm lại trong mỗi người\n" +
//                "Để ta không thấy ta như lúc này\n" +
//                "Đường xa quá dài rồi ta mệt nhoài\n" +
//                "Vì ta không đứng bên nhau nữa rồi",
//        genre = "Rock"
//    ),
//    SongData(
//        name = "Hotel California",
//        artists = listOf("Eagles"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/hotel%20california.jpg?alt=media&token=6bb05476-eac5-48e5-b558-8951fb74d26d",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Eagles%20%20Hotel%20California%20Lossless%20Audio.mp3?alt=media&token=34efc22e-37bd-4b38-87d8-666cd473c62c",
//        duration = (6 * 60 + 30) * 1000,
//        lyrics = "On a dark desert highway\n" +
//                "Cool wind in my hair\n" +
//                "Warm smell of colitas\n" +
//                "Rising up through the air\n" +
//                "Up ahead in the distance\n" +
//                "I saw a shimmering light\n" +
//                "My head grew heavy and my sight grew dim\n" +
//                "I had to stop for the night\n" +
//                "There she stood in the doorway\n" +
//                "I heard the mission bell\n" +
//                "And I was thinkin' to myself\n" +
//                "\"This could be heaven or this could be hell\"\n" +
//                "Then she lit up a candle\n" +
//                "And she showed me the way\n" +
//                "There were voices down the corridor\n" +
//                "I thought I heard them say\n" +
//                "\"Welcome to the Hotel California\n" +
//                "Such a lovely place (such a lovely place)\n" +
//                "Such a lovely face\n" +
//                "Plenty of room at the Hotel California\n" +
//                "Any time of year (any time of year)\n" +
//                "You can find it here\"\n" +
//                "Her mind is Tiffany-twisted\n" +
//                "She got the Mercedes-Benz, uh\n" +
//                "She got a lot of pretty, pretty boys\n" +
//                "That she calls friends\n" +
//                "How they dance in the courtyard\n" +
//                "Sweet summer sweat\n" +
//                "Some dance to remember\n" +
//                "Some dance to forget\n" +
//                "So I called up the Captain\n" +
//                "\"Please bring me my wine\"\n" +
//                "He said, \"We haven't had that spirit here\n" +
//                "Since 1969\"\n" +
//                "And still, those voices are calling\n" +
//                "From far away\n" +
//                "Wake you up in the middle of the night\n" +
//                "Just to hear them say\n" +
//                "\"Welcome to the Hotel California\n" +
//                "Such a lovely place (such a lovely place)\n" +
//                "Such a lovely face\n" +
//                "They're livin' it up at the Hotel California\n" +
//                "What a nice surprise (what a nice surprise)\n" +
//                "Bring your alibis\"\n" +
//                "Mirrors on the ceiling\n" +
//                "The pink champagne on ice\n" +
//                "And she said, \"We are all just prisoners here\n" +
//                "Of our own device\"\n" +
//                "And in the master's chambers\n" +
//                "They gathered for the feast\n" +
//                "They stab it with their steely knives\n" +
//                "But they just can't kill the beast\n" +
//                "Last thing I remember\n" +
//                "I was running for the door\n" +
//                "I had to find the passage back\n" +
//                "To the place I was before\n" +
//                "\"Relax, \" said the night man\n" +
//                "\"We are programmed to receive\n" +
//                "You can check out any time you like\n" +
//                "But you can never leave!\"",
//        genre = "Rock",
//    ),
//    SongData(
//        name = "Mary on a Cross",
//        artists = listOf("Ghost"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/mary%20on%20a%20cross.jpg?alt=media&token=0577e2ac-e15f-4b58-953c-b5434b635c8e",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Ghost%20%20Mary%20On%20A%20Cross%20Official%20Audio.mp3?alt=media&token=f0e78882-5ff9-430b-924a-b21466fd6f86",
//        duration = (4 * 60 + 4) * 1000,
//        lyrics = "Lời bài hát\n" +
//                "We were speeding together\n" +
//                "Down the dark avenues\n" +
//                "But besides of the stardom\n" +
//                "All we got was blues\n" +
//                "But through all of that sorrow\n" +
//                "We were riding high\n" +
//                "And the truth of the matter is\n" +
//                "I never let you go, let you go\n" +
//                "We were scanning the cities\n" +
//                "Rocking to pay the dues\n" +
//                "But besides of the glamour\n" +
//                "All we got was bruised\n" +
//                "But through all of that sorrow\n" +
//                "We were riding high\n" +
//                "And the truth of the matter is\n" +
//                "I never let you go, let you go\n" +
//                "You go down just like Holy Mary\n" +
//                "Mary on a, Mary on a cross\n" +
//                "Not just another Bloody Mary\n" +
//                "Mary on a, Mary on a cross\n" +
//                "If you choose to run away with me\n" +
//                "I will tickle you internally\n" +
//                "And I see nothing wrong with that\n" +
//                "We were searching for reasons\n" +
//                "To play by the rules\n" +
//                "But we quickly found\n" +
//                "It was just for fools\n" +
//                "Now through all of this sorrow\n" +
//                "We'll be riding high\n" +
//                "And the truth of the matter is\n" +
//                "I never let you go, let you go\n" +
//                "You go down just like Holy Mary\n" +
//                "Mary on a, Mary on a cross\n" +
//                "Not just another Bloody Mary\n" +
//                "Mary on a, Mary on a\n" +
//                "You go down just like Holy Mary\n" +
//                "Mary on a, Mary on a cross\n" +
//                "Your beauty never ever scared me\n" +
//                "Mary on a, Mary on a cross\n" +
//                "If you choose to run away with me\n" +
//                "I will tickle you internally\n" +
//                "And I see nothing wrong with that\n" +
//                "Nothing wrong with that\n" +
//                "(Mary on a, Mary on a cross)\n" +
//                "Nothing wrong with that\n" +
//                "(Mary on a, Mary on a cross)\n" +
//                "(Mary on a) Mary on a cross",
//        genre = "Rock"
//    ),
//    SongData(
//        name = "Can Can",
//        artists = listOf("Offenbach"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/offenbach.jpg?alt=media&token=21472ab8-3c48-4630-875c-dc9156be24ae",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Orpheus%20in%20the%20Underworld%20Can%20Can.mp3?alt=media&token=0e94c317-560a-4379-af69-7c1501a67f15",
//        duration = (2 * 60 + 11) * 1000,
//        lyrics = "",
//        genre = "Classical"
//    ),
//    SongData(
//        name = "Around the World (La La La La La)",
//        artists = listOf("A Touch of Class"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/download.jpg?alt=media&token=20eec48a-c597-46c4-90d7-cab09c00b7ca",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Around%20the%20World%20La%20La%20La%20La%20La%20Radio%20Version.mp3?alt=media&token=79f4aafc-9e3c-4743-b033-195e7fe68af9",
//        duration = (3 * 60 + 34) * 1000,
//        lyrics = "The kisses of the sun were sweet, I didn't blink\n" +
//                "I let it in my eyes like an exotic dream\n" +
//                "The radio playing songs that I have never heard\n" +
//                "I don't know what to say, oh, not another word\n" +
//                "Just la la la la la\n" +
//                "It goes around the world\n" +
//                "Just la la la la la\n" +
//                "It's all around the world\n" +
//                "Just la la la la la\n" +
//                "It goes around the world\n" +
//                "Just la la la la la\n" +
//                "It's all around the world\n" +
//                "La la la la la, la la la la la la la\n" +
//                "La la la la la, la la la la la la la\n" +
//                "La la la la la, la la la la la la la\n" +
//                "La la la la la\n" +
//                "It's all around the world just\n" +
//                "La la la la la\n" +
//                "La la la la la\n" +
//                "La la la la la\n" +
//                "La la la la la\n" +
//                "'Cause now the night is gone, still it goes on and on\n" +
//                "So deep inside of me, I long to set it free\n" +
//                "I don't know what to do, just can't explain to you\n" +
//                "I don't know what to say, oh, not another word just\n" +
//                "La la la la la (la la la la la la la)\n" +
//                "(La la la la la, la la la la la la la)\n" +
//                "(La la la la la, la la la la la la la)\n" +
//                "(La la la la la)\n" +
//                "It's all around the world\n" +
//                "Just la la la la la\n" +
//                "It goes around the world\n" +
//                "Just la la la la la\n" +
//                "It's all around the world\n" +
//                "Just la la la la la\n" +
//                "It goes around the world\n" +
//                "Just la la la la la\n" +
//                "It's all around the world\n" +
//                "Just la la la la la, la la la la la la la\n" +
//                "La la la la la, la la la la la la la\n" +
//                "La la la la la, la la la la la la la\n" +
//                "La la la la la\n" +
//                "It's all around the world just\n" +
//                "La la la la la\n" +
//                "La la la la la\n" +
//                "La la la la la\n" +
//                "La la la la la",
//        genre = "Dance/Electronic"
//    ),
//    SongData(
//        name = "Dancing in the Moonlight",
//        artists = listOf("Toploader"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/dancing%20in%20the%20moonlight.jpg?alt=media&token=69421173-9579-424a-b56f-fc67890f5040",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Toploader%20%20Dancing%20in%20the%20Moonlight%20Official%20Video.mp3?alt=media&token=4c5c7cb3-7f89-4758-9313-85906a8cabc4",
//        duration = (3 * 60 + 45) * 1000,
//        lyrics = "We get it almost every night\n" +
//                "When that moon is big and bright\n" +
//                "It's a supernatural delight\n" +
//                "Everybody's dancin' in the moonlight\n" +
//                "Everybody here is out of sight\n" +
//                "They don't bark, and they don't bite\n" +
//                "They keep things loose, they keep 'em tight\n" +
//                "Everybody was dancin' in the moonlight\n" +
//                "Dancin' in the moonlight\n" +
//                "Everybody's feelin' warm and bright\n" +
//                "It's such a fine and natural sight\n" +
//                "Everybody's dancin' in the moonlight\n" +
//                "We like our fun and we never fight\n" +
//                "You can't dance and stay uptight\n" +
//                "It's a supernatural delight\n" +
//                "Everybody was dancin' in the moonlight\n" +
//                "Dancin' in the moonlight\n" +
//                "Everybody's feelin' warm and bright\n" +
//                "It's such a fine and natural sight\n" +
//                "Everybody's dancin' in the moonlight\n" +
//                "We get it almost every night\n" +
//                "When that moon is big and bright\n" +
//                "It's a supernatural delight\n" +
//                "Everybody's dancin' in the moonlight\n" +
//                "Dancin' in the moonlight\n" +
//                "Everybody's feelin' warm and bright\n" +
//                "It's such a fine and natural sight\n" +
//                "Everybody's dancin' in the moonlight\n" +
//                "Dancin' in the moonlight\n" +
//                "Everybody's feelin' warm and bright\n" +
//                "It's such a fine and natural sight\n" +
//                "Everybody's dancin' in the moonlight\n" +
//                "Dancin' in the moonlight\n" +
//                "Everybody's feelin' warm and bright\n" +
//                "It's such a fine and natural sight\n" +
//                "Everybody's dancin' in the moonlight\n" +
//                "Dancin' in the moonlight\n" +
//                "Everybody's feelin' warm and bright\n" +
//                "It's such a fine and natural sight\n" +
//                "Everybody's dancin' in the moonlight\n" +
//                "Dancin' in the moonlight\n" +
//                "Everybody's feelin' warm and bright\n" +
//                "It's such a fine and natural sight\n" +
//                "Everybody's dancin' in the moonlight",
//        genre = "Rock"
//    ),
//    SongData(
//        name = "Nối Vòng Tay Lớn",
//        artists = listOf("Trịnh Công Sơn", "Đinh Ngọc Đức"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/noi%20vong%20tay%20lon.jpg?alt=media&token=8e7f78b8-4753-48ab-bfe0-5c67caa02ef3",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Noi%20Vong%20Tay%20Lon%20by%20Rock%20Viet%20Bands%20official%20MV.mp3?alt=media&token=edac0ae9-4fca-4b85-b055-fc3353c1511a",
//        duration = (5 * 60 + 20) * 1000,
//        lyrics = "(Rừng núi dang tay nối lại biển xa)\n" +
//                "(Ta đi vòng tay lớn mãi để nối sơn hà)\n" +
//                "(Mặt đất bao la anh em ta về)\n" +
//                "(Gặp nhau mừng như bão cát quay cuồng trời rộng)\n" +
//                "(Bàn tay ta nắm nối trọn một vòng Việt Nam)\n" +
//                "Rừng núi dang tay nối lại biển xa\n" +
//                "Ta đi vòng tay lớn mãi để nối sơn hà\n" +
//                "Mặt đất bao la anh em ta về\n" +
//                "Gặp nhau mừng như bão cát quay cuồng trời rộng\n" +
//                "Bàn tay ta nắm nối trọn một vòng Việt Nam\n" +
//                "Từ Bắc vô Nam nối liền nắm tay\n" +
//                "Ta đi từ đồng hoang vu vượt hết núi đồi\n" +
//                "Vượt thác cheo leo tay ta vượt đèo\n" +
//                "Từ quê nghèo lên phố lớn nắm tay nối liền\n" +
//                "Biển xanh sông gấm nối liền một vòng tử sinh\n" +
//                "Cờ nối gió đêm vui nối ngày\n" +
//                "Dòng máu nối con tim đồng loại\n" +
//                "Dựng tình người trong ngày mới\n" +
//                "Thành phố nối thôn xa vời vợi\n" +
//                "Người chết nối linh thiêng vào đời\n" +
//                "Và nụ cười nở trên môi\n" +
//                "Rừng núi dang tay nối lại biển xa\n" +
//                "Ta đi vòng tay lớn mãi để nối sơn hà\n" +
//                "Mặt đất bao la anh em ta về\n" +
//                "Gặp nhau mừng như bão cát quay cuồng trời rộng\n" +
//                "Bàn tay ta nắm nối trọn một vòng Việt Nam\n" +
//                "Từ Bắc vô Nam nối liền nắm tay\n" +
//                "Ta đi từ đồng hoang vu vượt hết núi đồi\n" +
//                "Vượt thác cheo leo tay ta vượt đèo\n" +
//                "Từ quê nghèo lên phố lớn nắm tay nối liền\n" +
//                "Biển xanh sông gấm nối liền một vòng tử sinh\n" +
//                "Cờ nối gió đêm vui nối ngày\n" +
//                "Dòng máu nối con tim đồng loại\n" +
//                "Dựng tình người trong ngày mới\n" +
//                "Thành phố nối thôn xa vời vợi\n" +
//                "Người chết nối linh thiêng vào đời\n" +
//                "Và nụ cười nở trên môi\n" +
//                "Cờ nối gió đêm vui nối ngày\n" +
//                "Dòng máu nối con tim đồng loại\n" +
//                "Dựng tình người trong ngày mới\n" +
//                "Thành phố nối thôn xa vời vợi\n" +
//                "Người chết nối linh thiêng vào đời\n" +
//                "Và nụ cười nở trên môi",
//        genre = "Rock"
//    ),
//    SongData(
//        name = "Wake Me Up",
//        artists = listOf("Avicii"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/wake%20me%20up.jpg?alt=media&token=88f7ea2c-5c0e-403e-a00b-e38db7dd908b",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Avicii%20%20Wake%20Me%20Up%20Official%20Video.mp3?alt=media&token=8566e201-f26d-4a48-95f7-15f68f2aa9b1",
//        duration = (4 * 60 + 32) * 1000,
//        lyrics = "Feeling my way through the darkness\n" +
//                "Guided by a beating heart\n" +
//                "I can't tell where the journey will end\n" +
//                "But I know where to start\n" +
//                "They tell me I'm too young to understand\n" +
//                "They say I'm caught up in a dream\n" +
//                "Well life will pass me by if I don't open up my eyes\n" +
//                "Well that's fine by me\n" +
//                "So wake me up when it's all over\n" +
//                "When I'm wiser and I'm older\n" +
//                "All this time I was finding myself, and I\n" +
//                "Didn't know I was lost\n" +
//                "So wake me up when it's all over\n" +
//                "When I'm wiser and I'm older\n" +
//                "All this time I was finding myself, and I\n" +
//                "Didn't know I was lost\n" +
//                "I tried carrying the weight of the world\n" +
//                "But I only have two hands\n" +
//                "Hope I get the chance to travel the world\n" +
//                "But I don't have any plans\n" +
//                "Wish that I could stay forever this young\n" +
//                "Not afraid to close my eyes\n" +
//                "Life's a game made for everyone\n" +
//                "And love is a prize\n" +
//                "So wake me up when it's all over\n" +
//                "When I'm wiser and I'm older\n" +
//                "All this time I was finding myself, and I\n" +
//                "Didn't know I was lost\n" +
//                "So wake me up when it's all over\n" +
//                "When I'm wiser and I'm older\n" +
//                "All this time I was finding myself, and I\n" +
//                "I didn't know I was lost\n" +
//                "I didn't know I was lost\n" +
//                "I didn't know I was lost\n" +
//                "I didn't know I was lost\n" +
//                "I didn't know",
//        genre = "Dance/Elctronic"
//    ),
//    SongData(
//        name = "Fly Me To The Moon",
//        artists = listOf("Frank Sinatra"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/fly%20me%20to%20the%20moon.jpg?alt=media&token=7e978451-ea68-4a7f-b4cc-34ef6fdd550b",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Fly%20Me%20To%20The%20Moon%202008%20Remastered.mp3?alt=media&token=0fd9b7b7-dc2e-4f20-968e-1c510ff957da",
//        duration = (2 * 60 + 27) * 1000,
//        lyrics = "Fly me to the moon\n" +
//                "Let me play among the stars\n" +
//                "Let me see what spring is like on\n" +
//                "A-Jupiter and Mars\n" +
//                "In other words, hold my hand\n" +
//                "In other words, baby, kiss me\n" +
//                "Fill my heart with song and let me sing forevermore\n" +
//                "You are all I long for\n" +
//                "All I worship and adore\n" +
//                "In other words, please be true\n" +
//                "In other words, I love you\n" +
//                "Fill my heart with song\n" +
//                "Let me sing forevermore\n" +
//                "You are all I long for, all I worship and adore\n" +
//                "In other words, please be true\n" +
//                "In other words\n" +
//                "In other words\n" +
//                "I love you",
//        genre = "Pop"
//    ),
//    SongData(
//        name = "Seven Nation Army",
//        artists = listOf("The White Stripes"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/seven%20nation%20army.jpg?alt=media&token=eee4470d-70d4-44cf-829b-5f5b58d2506b",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20The%20White%20Stripes%20%20Seven%20Nation%20Army%20Official%20Music%20Video.mp3?alt=media&token=bf51a894-5f56-4a0d-8718-d2a44920d97f",
//        duration = (3 * 60 + 59) * 1000,
//        lyrics = "I'm gonna fight 'em off\n" +
//                "A seven nation army couldn't hold me back\n" +
//                "They're gonna rip it off\n" +
//                "Takin' their time right behind my back\n" +
//                "And I'm talkin' to myself at night\n" +
//                "Because I can't forget\n" +
//                "Back and forth through my mind\n" +
//                "Behind a cigarette\n" +
//                "And the message comin' from my eyes\n" +
//                "Says, \"Leave it alone\"\n" +
//                "Don't wanna hear about it\n" +
//                "Every single one's got a story to tell\n" +
//                "Everyone knows about it\n" +
//                "From the Queen of England to the Hounds of Hell\n" +
//                "And if I catch it comin' back my way\n" +
//                "I'm gonna serve it to you\n" +
//                "And that ain't what you want to hear\n" +
//                "But that's what I'll do\n" +
//                "And the feelin' comin' from my bones\n" +
//                "Says, \"Find a home\"\n" +
//                "I'm goin' to Wichita\n" +
//                "Far from this opera forevermore\n" +
//                "I'm gonna work the straw\n" +
//                "Make the sweat drip out of every pore\n" +
//                "And I'm bleedin', and I'm bleedin', and I'm bleedin'\n" +
//                "Right before the Lord\n" +
//                "All the words are gonna bleed from me\n" +
//                "And I will think no more\n" +
//                "And the stains comin' from my blood\n" +
//                "Tell me, \"Go back home\"",
//        genre = "Rock"
//    ),
//    SongData(
//        name = "Take Me Home, Country Roads",
//        artists = listOf("John Denver"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/country%20road%20take%20me%20home.jpg?alt=media&token=aad5cec8-e442-4b5c-a93b-f06344add48d",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20John%20Denver%20%20Take%20Me%20Home%20Country%20Roads%20Official%20Audio.mp3?alt=media&token=26be593d-b6fa-4013-b583-56041c9f1476",
//        duration = (3 * 60 + 18) * 1000,
//        lyrics = "Almost heaven, West Virginia\n" +
//                "Blue Ridge Mountains, Shenandoah River\n" +
//                "Life is old there, older than the trees\n" +
//                "Younger than the mountains, growin' like a breeze\n" +
//                "Country roads, take me home\n" +
//                "To the place I belong\n" +
//                "West Virginia, mountain mama\n" +
//                "Take me home, country roads\n" +
//                "All my memories gather 'round her\n" +
//                "Miner's lady, stranger to blue water\n" +
//                "Dark and dusty, painted on the sky\n" +
//                "Misty taste of moonshine, teardrop in my eye\n" +
//                "Country roads, take me home\n" +
//                "To the place I belong\n" +
//                "West Virginia, mountain mama\n" +
//                "Take me home, country roads\n" +
//                "I hear her voice in the mornin' hour, she calls me\n" +
//                "The radio reminds me of my home far away\n" +
//                "Drivin' down the road, I get a feelin'\n" +
//                "That I should've been home yesterday, yesterday\n" +
//                "Country roads, take me home\n" +
//                "To the place I belong\n" +
//                "West Virginia, mountain mama\n" +
//                "Take me home, country roads\n" +
//                "Country roads, take me home\n" +
//                "To the place I belong\n" +
//                "West Virginia, mountain mama\n" +
//                "Take me home, country roads\n" +
//                "Take me home, (down) country roads\n" +
//                "Take me home, (down) country roads",
//        genre = "Country"
//    ),
//    SongData(
//        name = "(I Just) Died In Your Arms",
//        artists = listOf("Cutting Crew"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/i%20just%20died%20in%20your%20arms%20tonight.jpg?alt=media&token=5daeef51-3d34-48bd-9cc3-da79b620568b",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Cutting%20Crew%20%20I%20Just%20Died%20In%20Your%20Arms%20Official%20Music%20Video.mp3?alt=media&token=1f8591b3-48ee-471d-b7ff-5de140a11aab",
//        duration = (4 * 60 + 32) * 1000,
//        lyrics = "Oh I, I just died in your arms tonight\n" +
//                "It must have been something you said\n" +
//                "I just died in your arms tonight\n" +
//                "I keep lookin' for somethin' I can't get\n" +
//                "Broken hearts lie all around me\n" +
//                "And I don't see an easy way to get out of this\n" +
//                "Her diary, it sits by the bedside table\n" +
//                "The curtains are closed, the cats in the cradle\n" +
//                "Who would've thought that a boy like me could come to this\n" +
//                "Oh I, I just died in your arms tonight\n" +
//                "It must've been something you said\n" +
//                "I just died in your arms tonight\n" +
//                "Oh I, I just died in your arms tonight\n" +
//                "It must've been some kind of kiss\n" +
//                "I should've walked away\n" +
//                "I should've walked away\n" +
//                "Is there any just cause for feelin' like this?\n" +
//                "On the surface, I'm a name on a list\n" +
//                "I try to be discreet, but then blow it again\n" +
//                "I've lost and found, it's my final mistake\n" +
//                "She's loving by proxy, no give and all take\n" +
//                "'Cause I've been thrilled to fantasy one too many times\n" +
//                "Oh I, I just died in your arms tonight\n" +
//                "It must've been something you said\n" +
//                "I just died in your arms tonight\n" +
//                "Oh I, I just died in your arms tonight\n" +
//                "It must've been some kind of kiss\n" +
//                "I should've walked away\n" +
//                "I should've walked away\n" +
//                "It was a long hot night\n" +
//                "She made it easy, she made it feel right\n" +
//                "But now it's over, the moment has gone\n" +
//                "I followed my hands not my head, I know I was wrong\n" +
//                "Oh I, I just died in your arms tonight\n" +
//                "It must've been something you said\n" +
//                "I just died in your arms tonight\n" +
//                "I, I just died in your arms tonight\n" +
//                "It must've been some kind of kiss\n" +
//                "I should've walked away\n" +
//                "I should've walked away\n",
//        genre = "Rock"
//    ),
//
//    SongData(
//        name = "Lonely Boy",
//        artists = listOf("The Black Keys"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/lonely%20boy.jpg?alt=media&token=43c53f47-51eb-4779-9442-379a061aee6d",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20The%20Black%20Keys%20%20Lonely%20Boy%20Official%20Music%20Video.mp3?alt=media&token=5ac505b3-0783-4185-be1d-0281b53e3026",
//        duration = (3 * 60 + 15) * 1000,
//        lyrics = "Well, I'm so above you\n" +
//                "And it's plain to see\n" +
//                "But I came to love you anyway\n" +
//                "So you pulled my heart out\n" +
//                "And I don't mind bleedin'\n" +
//                "Any old time, you keep me waitin'\n" +
//                "Waitin', waitin'\n" +
//                "Oh, whoa, oh\n" +
//                "I got a love that keeps me waitin'\n" +
//                "Oh, whoa, oh\n" +
//                "I got a love that keeps me waitin'\n" +
//                "I'm a lonely boy\n" +
//                "I'm a lonely boy\n" +
//                "Oh, whoa, oh\n" +
//                "I got a love that keeps me waitin'\n" +
//                "Well, your mama kept you\n" +
//                "But your daddy left you\n" +
//                "And I should've done you just the same\n" +
//                "But I came to love you\n" +
//                "Am I born to bleed?\n" +
//                "Any old time, you keep me waitin'\n" +
//                "Waitin', waitin'\n" +
//                "Oh, whoa, oh\n" +
//                "I got a love that keeps me waitin'\n" +
//                "Oh, whoa, oh\n" +
//                "I got a love that keeps me waitin'\n" +
//                "I'm a lonely boy\n" +
//                "I'm a lonely boy\n" +
//                "Oh, whoa, oh\n" +
//                "I got a love that keeps me waitin'\n" +
//                "Hey\n" +
//                "Oh, whoa, oh\n" +
//                "I got a love that keeps me waitin'\n" +
//                "Oh, whoa, oh\n" +
//                "I got a love that keeps me waitin'\n" +
//                "I'm a lonely boy\n" +
//                "I'm a lonely boy\n" +
//                "Oh, whoa, oh\n" +
//                "I got a love that keeps me waitin'",
//        genre = "Rock"
//    ),
//    SongData(
//        name = "Don't Stop Believin'",
//        artists = listOf("Journey"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/don't%20stop%20believin'.jpg?alt=media&token=68913482-4636-43ce-a9ae-b198a4cbade3",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Journey%20%20Dont%20Stop%20Believin%20Official%20Audio.mp3?alt=media&token=1312e720-23ae-44e2-ab7d-8ba38437dd1d",
//        duration = (4 * 60 + 10) * 1000,
//        lyrics = "Just a small town girl\n" +
//                "Livin' in a lonely world\n" +
//                "She took the midnight train going anywhere\n" +
//                "Just a city boy\n" +
//                "Born and raised in South Detroit\n" +
//                "He took the midnight train going anywhere\n" +
//                "A singer in a smokey room\n" +
//                "A smell of wine and cheap perfume\n" +
//                "For a smile they can share the night\n" +
//                "It goes on and on and on and on\n" +
//                "Strangers waitin'\n" +
//                "Up and down the boulevard\n" +
//                "Their shadows searchin' in the night\n" +
//                "Streetlights, people\n" +
//                "Livin' just to find emotion\n" +
//                "Hidin', somewhere in the night\n" +
//                "Workin' hard to get my fill\n" +
//                "Everybody wants a thrill\n" +
//                "Payin' anything to roll the dice\n" +
//                "Just one more time\n" +
//                "Some'll win, some will lose\n" +
//                "Some are born to sing the blues\n" +
//                "Whoa, the movie never ends\n" +
//                "It goes on and on and on and on\n" +
//                "Strangers waitin'\n" +
//                "Up and down the boulevard\n" +
//                "Their shadows searchin' in the night\n" +
//                "Streetlights, people\n" +
//                "Livin' just to find emotion\n" +
//                "Hidin', somewhere in the night\n" +
//                "Don't stop believin'\n" +
//                "Hold on to that feelin'\n" +
//                "Streetlights, people\n" +
//                "Don't stop believin'\n" +
//                "Hold on\n" +
//                "Streetlights, people\n" +
//                "Don't stop believin'\n" +
//                "Hold on to that feelin'\n" +
//                "Streetlights, people",
//        genre = "Rock"
//    ),
//    SongData(
//        name = "Song from a Secret Garden",
//        artists = listOf("Secret Garden"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/secret%20garden.jpg?alt=media&token=c7a15329-2a71-4352-9855-969727c4db07",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Secret%20Garden%20Song%20from%20a%20Secret%20Garden.mp3?alt=media&token=bfd37b53-7763-46eb-b790-d79825eb7bd9",
//        duration = (3 * 60 + 34) * 1000,
//        lyrics = "",
//        genre = "Classical"
//    ),
//    SongData(
//        name = "Heart of Courage",
//        artists = listOf("Two Steps From Hell"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/heart%20of%20courage.jpg?alt=media&token=f730dd85-b036-42ad-ac94-48f867896c68",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%20Heart%20of%20Courage.mp3?alt=media&token=5e3dbdc5-913a-414c-b2b2-842b35af4b82",
//        duration = (1 * 60 + 59) * 1000,
//        lyrics = "",
//        genre = "Classical"
//    ),
//    SongData(
//        name = "Hooked on a Feeling",
//        artists = listOf("Blue Swede"),
//        imageUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/hooked%20on%20a%20feeling.jpg?alt=media&token=1d78f409-ae02-4cbe-98b4-98fd057a1501",
//        musicUrl = "https://firebasestorage.googleapis.com/v0/b/spotify-1bf9e.appspot.com/o/y2mate.com%20-%201%20Blue%20Swede%20%20Hooked%20on%20a%20Feeling.mp3?alt=media&token=30043738-5575-4c7f-b03f-fbd248c02fcd",
//        duration = (2 * 60 + 47) * 1000,
//        lyrics = "Ooga-Chaka Ooga-Ooga\n" +
//                "Ooga-Chaka Ooga-Ooga\n" +
//                "Ooga-Chaka Ooga-Ooga\n" +
//                "Ooga-Chaka Ooga-Ooga\n" +
//                "I can't stop this feeling\n" +
//                "Deep inside of me\n" +
//                "Girl, you just don't realize\n" +
//                "What you do to me\n" +
//                "When you hold me\n" +
//                "In your arms so tight\n" +
//                "You let me know\n" +
//                "Everything's alright\n" +
//                "I'm hooked on a feeling\n" +
//                "I'm high on believing\n" +
//                "That you're in love with me\n" +
//                "Lips as sweet as candy\n" +
//                "It's taste is on my mind\n" +
//                "Girl, you got me thirsty\n" +
//                "For another cup of wine\n" +
//                "Got a bug from you girl\n" +
//                "But I don't need no cure\n" +
//                "I just stay a victim\n" +
//                "If I can for sure\n" +
//                "All the good love\n" +
//                "When we're all alone\n" +
//                "Keep it up girl\n" +
//                "Yeah, you turn me on\n" +
//                "I'm hooked on a feeling\n" +
//                "I'm high on believing\n" +
//                "That you're in love with me\n" +
//                "All the good love\n" +
//                "When we're all alone\n" +
//                "Keep it up, girl\n" +
//                "Yeah, you turn me on\n" +
//                "I'm hooked on a feeling\n" +
//                "I'm high on believing\n" +
//                "That you're in love with me\n" +
//                "I'm hooked on a feeling\n" +
//                "And I'm high on believing\n" +
//                "That you're in love with me\n" +
//                "I said I'm hooked on a feeling\n" +
//                "And I'm high on believing\n" +
//                "That you're in love with me\n" +
//                "I'm hooked on a feeling",
//        genre = "Pop"
//    ),
//)
