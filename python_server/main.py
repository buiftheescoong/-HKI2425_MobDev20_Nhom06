from spleeter.separator import Separator
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import requests
import os
from fastapi.responses import FileResponse
from fastapi.middleware.cors import CORSMiddleware

app = FastAPI()
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Cho phép tất cả các nguồn (hoặc thay "*" bằng các URL cụ thể)
    allow_credentials=True,
    allow_methods=["*"],  # Cho phép tất cả các phương thức HTTP
    allow_headers=["*"],  # Cho phép tất cả các headers
)

# Request model
class AudioRequest(BaseModel):
    url: str  

@app.post("/process-audio")
async def process_audio(audio_request: AudioRequest):
    url = audio_request.url
    input_audio = "input_audio.mp3"
    output_dir = "output"

    try:
        # Bước 1: Tải file MP3 từ URL
        response = requests.get(url)
        with open(input_audio, "wb") as file:
            file.write(response.content)

        # Bước 2: Tách nhạc với Spleeter
        separator = Separator('spleeter:2stems')  # Tách vocal và accompaniment
        separator.separate_to_file(input_audio, output_dir)

        # Bước 3: Đường dẫn file karaoke track
        karaoke_track_path = os.path.join(output_dir, "input_audio", "accompaniment.wav")

        # Kiểm tra nếu karaoke track đã được tạo thành công
        if os.path.exists(karaoke_track_path):
            # Trả về file karaoke track
            return FileResponse(karaoke_track_path, media_type="audio/wav", filename="karaoke_track.wav")
        else:
            raise HTTPException(status_code=500, detail="Failed to extract karaoke track")

    except Exception as e:
        # Xử lý lỗi
        raise HTTPException(status_code=500, detail=str(e))

    finally:
        # Xóa file tải về sau khi hoàn thành
        if os.path.exists(input_audio):
            os.remove(input_audio)
