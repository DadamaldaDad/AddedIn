import json
from pathlib import Path
from typing import Any
import re

import pandas as pd

def print_hi(name):
    # Use a breakpoint in the code line below to debug your script.
    print(f'Hi, {name}')  # Press Ctrl+8 to toggle the breakpoint.

def namespace_id(id: str):
    return id if id.find(":") != -1 else "minecraft:"+id

def clean_version(version: str) -> str:
    split_version = version.split()
    match version.split(" ")[0]:
        case "Alpha":
            return f"Alpha {split_version[1][1:]}"
        case "Beta":
            return f"Beta {split_version[1]}"
        case _:
            return split_version[0]


potion_regex = re.compile(r"(\w*)\[potion_contents={potion:\"([\w:]+)\"}]")


def potion_id(row) -> str:
    id = row["id"]
    match = potion_regex.match(id)
    if match is None: return ""
    return match.groups()[1]

def potion_type(row) -> str:
    id = row["id"]
    match = potion_regex.match(id)
    if match is None: return ""
    return {
        "potion": "version",
        "splash_potion": "splash_version",
        "lingering_potion": "lingering_version",
        "lingering__potion": "lingering_version",
        "tipped_arrow": "arrow_version"
    }[match.groups()[0]]


manual_inserts: list[tuple[str, str]] = [
    ("chain", "1.16")
]


def run():
    full_df = pd.read_csv("./input.csv", skiprows=4, header=None)
    assert isinstance(full_df, pd.DataFrame)
    df_unfiltered: pd.DataFrame = full_df.iloc[:, [2, 3]]
    df_unfiltered.columns = ["id", "version"]
    df_unfiltered["version"] = df_unfiltered["version"].apply(clean_version)
    print("Loaded data")
    mask = ~df_unfiltered["id"].str.contains(r"\[", regex=True)
    df: pd.DataFrame = df_unfiltered[mask]
    df["id"] = df["id"].apply(namespace_id)
    prepared_manual_inserts = [{"id": namespace_id(row[0]), "version": row[1]} for row in manual_inserts]
    df = pd.concat([df, pd.DataFrame(prepared_manual_inserts)], ignore_index=True)
    result: dict[str, Any] = {
        row["id"]: {"version": row["version"]}
        for index, row in df.iterrows()
    }
    result_json = json.dumps({"values": result}, indent=4)
    Path("./version_data.json").write_text(result_json)
    print("Items done")
    potion_mask = df_unfiltered["id"].str.contains(r"\[potion_contents=\{")
    potion_df: pd.DataFrame = df_unfiltered[potion_mask]
    potion_df["potion_id"] = potion_df.apply(potion_id, axis=1)
    potion_df["potion_type"] = potion_df.apply(potion_type, axis=1)
    potions: dict[str, dict[str, str]] = {}
    for index, row in potion_df.iterrows():
        if row["potion_id"] not in potions.keys():
            potions[row["potion_id"]] = {
                "version": "???",
                "splash_version": "???",
                "lingering_version": "???",
                "arrow_version": "???"
            }
        potions[row["potion_id"]][row["potion_type"]] = row["version"]
    potions_json = json.dumps({"values": potions}, indent=4)
    Path("./potion_version_data.json").write_text(potions_json)
    print("Potions done")
    print("Done, bye.")

# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    run()

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
