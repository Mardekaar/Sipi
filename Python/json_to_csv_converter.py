import json
import csv
from collections import defaultdict

def process_json_to_csv(input_json_path, output_csv_path):
    # Read the JSON file
    with open(input_json_path, 'r', encoding='utf-8') as json_file:
        data = json.load(json_file)

    # Dictionary to store content by languages
    all_languages = set()
    contents_by_section = []

    # Process each template in the data
    for template in data:
        for section in template['sections']:
            if section['contents']:  # Only process sections that have contents
                content_dict = {}
                # Process each content in the section
                for content in section['contents']:
                    language = content['language']
                    content_text = content['content']
                    content_dict[language] = content_text
                    all_languages.add(language)

                if content_dict:  # Only add if we found any content
                    contents_by_section.append(content_dict)

    # Convert languages set to sorted list to ensure consistent column order
    languages = sorted(list(all_languages))

    # Write to CSV
    with open(output_csv_path, 'w', encoding='utf-8', newline='') as csv_file:
        writer = csv.writer(csv_file)

        # Write header row with language codes
        writer.writerow(languages)

        # Write content rows
        for content_dict in contents_by_section:
            # Create a row with content for each language (or empty string if no content)
            row = [content_dict.get(lang, '') for lang in languages]
            writer.writerow(row)

    print(f"CSV file has been created at: {output_csv_path}")
    print(f"Processed {len(contents_by_section)} sections with content in {len(languages)} languages: {', '.join(languages)}")

if __name__ == "__main__":
    # Example usage
    input_json_path = "input.json"  # Replace with your JSON file path
    output_csv_path = "output.csv"  # Replace with desired output CSV path

    try:
        process_json_to_csv(input_json_path, output_csv_path)
    except Exception as e:
        print(f"Error occurred: {e}")