import sys
from telethon import TelegramClient

def send_apk_part(file_path, chat_id, bot_token, description):
    # Crie o cliente do Telegram
    client = TelegramClient('bot', api_id, api_hash).start(bot_token=bot_token)

    # Envie o documento
    with client:
        client.send_file(chat_id, file_path, caption=description)

if __name__ == "__main__":
    if len(sys.argv) != 5:
        print("Uso: python send_apk.py <file_path> <chat_id> <bot_token> <description>")
        sys.exit(1)

    file_path = sys.argv[1]
    chat_id = sys.argv[2]
    bot_token = sys.argv[3]
    description = sys.argv[4]

    send_apk_part(file_path, chat_id, bot_token, description)
