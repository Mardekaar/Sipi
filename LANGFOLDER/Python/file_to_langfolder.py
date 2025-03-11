import os
import json
import shutil

# Get the current directory
current_folder = os.getcwd()

specific_extension = ".xlf"  # Change this to the desired extension

# Read the language mappings from langmap.txt
lang_map_file = os.path.join(current_folder, "langmap.txt")
lang_map = {}

if os.path.exists(lang_map_file):
    with open(lang_map_file, 'r', encoding='utf-8') as f:
        lang_map_content = json.load(f)
        lang_map = lang_map_content
        for key, value in lang_map.items():
            print(f"Mapping: {key} -> {value}")
else:
    print("langmap.txt not found in the current folder.")
    exit()

# Iterate over files in the current directory with the specific extension
for file_name in os.listdir(current_folder):
    if file_name.endswith(specific_extension):
        file_name_without_extension = os.path.splitext(file_name)[0]
        parts = file_name_without_extension.split('_')
        str_lang = parts[-1]  # Get the last part after the underscore

        mapped_lang = lang_map.get(str_lang, str_lang)  # Use the original language code if no mapping is found
        print(f"Mapped lang: {mapped_lang}")

        str_folder_path = os.path.join(current_folder, mapped_lang)

        if not os.path.exists(str_folder_path):
            os.makedirs(str_folder_path)

        shutil.move(os.path.join(current_folder, file_name), str_folder_path)